package com.distributed.problem.feign;

import com.distributed.problem.domain.Problem;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@FeignClient(value = "problem" )
public interface feignClient {

    @GetMapping(value = "/api/v1/problems/{code}/info", produces = "application/json")
    @ApiOperation(value = "code가 일치하는 problem 리스트 조회" , produces =  "http")
    ResponseEntity getProblemsByCode(@PathVariable("code") String code);

    @GetMapping(value = "/api/v1/problems/{code}/info/{id}")
    @ApiOperation(value = "code와 id가 일치하는 problem 조회 ", protocols = "http")
    ResponseEntity getProblemByCodeAndId(@PathVariable("code") String code, @PathVariable("id") Integer id);

    @PostMapping(value = "/api/v1/problem", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "problem 생성" , protocols = "http")
    ResponseEntity createProblemWithCode(@RequestBody @Valid Problem problem);

    @DeleteMapping(value = "/api/v1/problem/{code}", produces = "application/json")
    @ApiOperation(value = "delete problem with code", protocols = "http")
    ResponseEntity deleteProblemWithCode(@PathVariable String code);


}
