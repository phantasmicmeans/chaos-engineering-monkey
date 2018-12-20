package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface subProblemClient {

    @GetMapping(value = "v1/sub-problem/{id}", produces = {"application/json"})
    @ApiOperation(value = "id로 sub-problem조회", produces = "http")
    ResponseEntity getSubProblemById(@PathVariable("id") Integer id);

    @GetMapping(value = "v1/sub-problem/list/{code}", produces = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 전부 조회", protocols = "http")
    ResponseEntity getSubProblemByCode(@PathVariable("code") String code);

    @GetMapping(value = "v1/sub-problem/{code}/info/{id}", consumes = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 중 index로 조회", protocols = "http")
    ResponseEntity getSubProblemByCodeAndId(@PathVariable("code") String code, @PathVariable("id") Long id);

    @PostMapping(value = "v1/sub-problem", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "sub-problem 생성", protocols = "http")
    ResponseEntity saveSubProblemByCode(@RequestBody subProblem subProblem);

    @PostMapping(value = "v1/sub-problem/list", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "sub-problem list 생성", produces = "http")
    @Headers("Content-Type: application/json")
    ResponseEntity saveSubProblemsByCode(@RequestBody List<subProblem> subProblemList) throws Exception;

}
