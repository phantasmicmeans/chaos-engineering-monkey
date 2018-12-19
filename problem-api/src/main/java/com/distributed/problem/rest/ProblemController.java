package com.distributed.problem.rest;

import com.distributed.problem.domain.Problem;
import com.distributed.problem.dto.problemDTO;
import com.distributed.problem.exception.DataInvalidException;
import com.distributed.problem.exception.ResourceNotFoundException;
import com.distributed.problem.feign.subProblem;
import com.distributed.problem.feign.subProblemClient;
import com.distributed.problem.repository.problemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ProblemController {

    @Autowired
    private subProblemClient subProblemClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private problemRepository proRepository;
    @Autowired
    private ProblemValidator problemValidator;
    @Autowired
    private EurekaClient eurekaClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/api/v1/problems/{code}/info", produces = "application/json")
    @ApiOperation(value = "code가 일치하는 problem 리스트 조회" , produces =  "http")
    public ResponseEntity getProblemsByCode(@PathVariable String code) throws Exception{ //code로 찾기

        //1. find problem with code, request to sub-problem api server - TRY
        problemValidator.checkIsEmpty(code); //validation
        List <Problem> problems = proRepository.findAllByCodeOrderByCode(code);
        if(problems.isEmpty()) throw new ResourceNotFoundException("cannot found problems with that code");
        //2. find sub-problem with code
        ResponseEntity <String> responseEntity = this.subProblemClient.getSubProblemByCode(code); //code, id 정렬 , 문제와 1 : N
        Optional.ofNullable(responseEntity)
                    .orElseThrow(() -> new ResourceNotFoundException("cannot found sub-problem with that code"));
        //3. add result set
        List<subProblem> json_object = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<subProblem>>(){}); //List로 바꿔
        //code 순서 같음.
        logger.info(objectMapper.writeValueAsString(problemValidator.makeSetOfProAndSubPro(problems, json_object)));
        return ResponseEntity.ok(objectMapper.writeValueAsString(problemValidator.makeSetOfProAndSubPro(problems, json_object)));

    }

    @GetMapping(value = "/api/v1/problem/{code}/info/{hash_code}")
    @ApiOperation(value = "code와 hash_code가 일치하는 problem 조회 ", protocols = "http")
    public ResponseEntity getProblemByCodeAndId(String code, String hash_code) { //code, id
        return null;
    }

    @PostMapping(value = "/api/v1/problem", consumes = "application/json", produces = "application/json")
    @Headers("Content-Type: application/json")
    @ApiOperation(value = "problem 생성" , protocols = "http")
    public ResponseEntity createProblemWithCode(@RequestBody  problemDTO problemDto) throws Exception{ //post
        //problem -> subproblem,
        logger.info(objectMapper.writeValueAsString(problemDto));
        if(problemDto.getTitle().length()==0 || !problemValidator.checkIsEmpty(problemDto.getCode())) //check
            throw new DataInvalidException("please check your problem - title");

        Problem problem = Problem.builder()
                                    .pro_hash(String.valueOf(problemDto.getCode().hashCode())) //problemDTO로부터 넘어온 code로 hash 생성
                                    .title(problemDto.getTitle()).code(problemDto.getCode())
                                    .localDate(LocalDate.now()).localDateTime(LocalDateTime.now())
                                    .build();

        List<subProblem> subProblems = problemDto.getSubProblemList(); //sub-problem list
        subProblems.forEach((instance) -> instance.setPro_hash(problem.getPro_hash())); //hash 지정
        logger.info("subproblems : " + objectMapper.writeValueAsString(subProblems));

        if(subProblems.isEmpty()) throw new DataInvalidException("please check your sub-problem data"); //check

        ResponseEntity<String> responseEntity = this.subProblemClient.saveSubProblemsByCode(subProblems);
        logger.info("response from subproblem : " + objectMapper.writeValueAsString(responseEntity.getBody()));

        problemDTO retDTO;
        String error = "";
        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
            try {//if created -> insert problem -> return created
                Problem p = this.proRepository.save(problem); //나중에 service 로 쪼개서 refactoring 필요
                retDTO = problemDTO.builder().code(p.getCode()).title(p.getTitle()).build(); //ret problem set
                retDTO.setSubProblemList(subProblems); //sub-problems set

                URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                        .path("api/v1/problem/{hash_code}") //uri
                        .build().expand(problem.getPro_hash()).toUri();
                return ResponseEntity.created(location).body(objectMapper.writeValueAsString(retDTO));
            } catch (Throwable e) { //else sub-problem에서 실패시 cause 던져줌. 그걸 던지자
                error = e.getCause().toString();
            }
        }
        return ResponseEntity.status(500).body(objectMapper.writeValueAsString(error)); //json exception
    }

    @DeleteMapping(value = "/api/v1/problem/{code}", produces = "application/json")
    @ApiOperation(value = "delete problem with code", protocols = "http")
    public ResponseEntity deleteProblemWithCode(String code) { //delete
        return null;
    }

}
