package com.distributed.problem.feign;

import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "subproblem-client", fallbackFactory = subProblemFallback.class)
public interface subProblemClient {

    @GetMapping(value = "api/v1/sub-problem/{id}", produces = {"application/json"})
    @ApiOperation(value = "id로 sub-problem조회", produces = "http")
    ResponseEntity getSubProblemById(@PathVariable("id") Long id);

    @GetMapping(value = "api/v1/sub-problems/{code}", produces = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 전부 조회", protocols = "http")
    ResponseEntity <String> getSubProblemByCode(@PathVariable("code") String code);

    @GetMapping(value = "api/v1/sub-problem/{code}/info/{id}", consumes = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 중 index로 조회", protocols = "http")
    ResponseEntity <String> getSubProblemByCodeAndId(@PathVariable("code") String code, @PathVariable("id") Long id);

    @PostMapping(value = "api/v1/sub-problem", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "sub-problem 생성", protocols = "http")
    ResponseEntity <String> saveSubProblemByCode(@RequestBody subProblem subProblem);

    @PostMapping(value = "api/v1/sub-problems", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "sub-problem list 생성", produces = "http")
    @Headers("Content-Type: application/json")
    ResponseEntity <String> saveSubProblemsByCode(@RequestBody List<subProblem> subProblemList);
}
