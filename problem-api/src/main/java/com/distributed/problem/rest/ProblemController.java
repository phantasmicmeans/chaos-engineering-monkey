package com.distributed.problem.rest;

import com.distributed.problem.domain.Problem;
import com.distributed.problem.eureka.ClientEureka;
import com.distributed.problem.exception.ResourceNotFoundException;
import com.distributed.problem.feign.subProblemClient;
import com.distributed.problem.repository.problemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.shared.Application;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class ProblemController {

    @Autowired
    private subProblemClient subProblemClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private problemRepository proRepository;
    @Autowired
    private EurekaClient eurekaClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/api/v1/problems/{code}/info", produces = "application/json")
    @ApiOperation(value = "code가 일치하는 problem 리스트 조회" , produces =  "http")
    public ResponseEntity getProblemsByCode(@PathVariable String code) throws Exception{ //code로 찾기

        Collection<Problem> problem = proRepository.findAllByCode(code);
        if(problem.isEmpty()) throw new ResourceNotFoundException("no problem with that code");

        ResponseEntity<String> responseEntity = this.subProblemClient.getSubProblemByCode(code);
        Optional.ofNullable(responseEntity).orElseThrow(() -> new ResourceNotFoundException("no sub-problem with that code"));

        //같은 code의 problem이 여러개고, problem별로 문제가 있을테니, 이걸 어떻게 구별?
        return responseEntity;

//
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://13.209.237.163:8080/eureka/actuator/info";
//        ResponseEntity response = restTemplate.getForEntity(url, String.class);
//        logger.info(response.toString());
//        return response;
    }


    @GetMapping(value = "/api/v1/problems/{code}/info/{id}")
    @ApiOperation(value = "code와 id가 일치하는 problem 조회 ", protocols = "http")
    public ResponseEntity getProblemByCodeAndId(String code, Integer id) { //code, id
        return null;
    }

    @PostMapping(value = "/api/v1/problem", consumes = "application/json", produces = "application/json")
    @Headers("Content-Type: application/json")
    @ApiOperation(value = "problem 생성" , protocols = "http")
    public ResponseEntity createProblemWithCode(@Valid Problem problem) { //post
        //transaction생각해서 짜야할듯 !
        return null;
    }

    @DeleteMapping(value = "/api/v1/problem/{code}", produces = "application/json")
    @ApiOperation(value = "delete problem with code", protocols = "http")
    public ResponseEntity deleteProblemWithCode(String code) { //delete
        return null;
    }
}
