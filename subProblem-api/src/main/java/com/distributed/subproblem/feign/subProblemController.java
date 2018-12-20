package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.DataInvalidException;
import com.distributed.subproblem.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class subProblemController implements subProblemClient{

    @Autowired
    private subProblemService subProblemService;

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ResponseEntity getSubProblemById(Integer id) {

        logger.info("getSubProblemById(Long id)");
        checkIsEmpty(id);
        subProblem subProblem = subProblemService.loadSubProblemById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("cannot found sub-problems with that id"));
        logger.info("getSubProblemById return");

        return ResponseEntity.ok(subProblem);
    }

    @Override
    public ResponseEntity getSubProblemByCode(String code) {

        logger.info("getSubProblemByCode(String code)");
        checkIsEmpty(code);
        List<subProblem> subProblems = subProblemService.loadSubProblemByCode(code);
        if(subProblems.isEmpty()) throw new ResourceNotFoundException("cannot found sub-problems with that code");
        logger.info("getSubProblemByCode return");

        return ResponseEntity.ok(subProblems);
    }

    @Override
    public ResponseEntity getSubProblemByCodeAndId(String code, Long id) {

        if(checkIsEmpty(code) && checkIsEmpty(id)){ }
        return null;
    }

    @Override
    public ResponseEntity saveSubProblemByCode(subProblem subProblem) throws ClassCastException{

        logger.info(subProblem.toString());
        if(!checkIsEmpty(subProblem)) { throw new DataInvalidException("please check your data"); }

        Optional<Object> object = Optional.ofNullable(subProblemService.saveSubProblem(subProblem));
        String error;

        Object retObject = object.get();
        if(retObject.getClass().equals(subProblem.getClass())){ //save, redirect get uri 함께
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("api/v1/sub-problem/{id}")
                            .build().expand(subProblem.getId()).toUri();

            return ResponseEntity.created(location).body(subProblem);
        }else{//save failure -> error code
            error = (String) object.get();
        }
        logger.info("saveSubProblemByCode return");

        return ResponseEntity.status(500).body(error); //internal server error with error code
    }

    @Override
    public ResponseEntity saveSubProblemsByCode(List<subProblem> subProblemList) throws Exception{

        logger.info("saveSubProblemByCode(List<subProblem> subproblem)");
        if(subProblemList.isEmpty()) throw new DataInvalidException("you must insert at least one sub-problem");

        Optional<Object> retObject = Optional.ofNullable(this.subProblemService.saveSubProblems(subProblemList));

        if(retObject.get().getClass().equals(subProblemList.getClass())) { //List<subProblem이면
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("api/v1/sub-problems/{code}")
                    .build().expand(subProblemList.get(0).getCode()).toUri();
            return ResponseEntity.created(location).body(objectMapper.writeValueAsString(retObject.get()));
        }
        logger.info("saveSubProblemsByCode return");
        //save failure -> throwable code;
        return ResponseEntity.status(500).body(objectMapper.writeValueAsString(retObject.get()));
    }

    public boolean checkIsEmpty(Object object){

        if(object.getClass().equals(String.class)) { //code - null, length check
            String code = (String) Optional.ofNullable(object).orElseThrow(()
                                -> new DataInvalidException("please check your {code} in uri or json data"));
            return code.length() == 6;

        }else if(object.getClass().equals(Integer.class)){ //id - null check
            Integer Id = (Integer) Optional.ofNullable(object).orElseThrow(()
                                -> new DataInvalidException("please check your {id} in uri or json data"));
            return true;

        }else if(object.getClass().equals(subProblem.class)){ //POST data
            subProblem subProblem = (subProblem) Optional.ofNullable(object).orElseThrow(()
                                -> new DataInvalidException("please check your json(post) data"));
            return checkIsEmpty(subProblem.getCode()) && subProblem.getContent() != null; //null pointer
        }
        return false;
    }
}
