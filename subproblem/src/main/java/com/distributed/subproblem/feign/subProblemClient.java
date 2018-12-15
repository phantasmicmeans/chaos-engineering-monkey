package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "subProblem-client", configuration = subProblemConfiguration.class,
                fallbackFactory = subProblemFallback.class, primary = false)
public interface subProblemClient {

    @GetMapping(value = "api/v1/sub-problem/{id}", consumes = {"application/json"})
    @ApiOperation(value = "id로 sub-problem조회", produces = "http")
    ResponseEntity getSubProblemById(@PathVariable("id") Long id);

    @GetMapping(value = "api/v1/sub-problem/{code}", consumes = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 전부 조회", protocols = "http")
    ResponseEntity getSubProblemByCode(@PathVariable("code") String code);

    @GetMapping(value = "api/v1/sub-problem/{code}/info/{id}", consumes = {"application/json"})
    @ApiOperation(value = "code가 일치하는 sub-problem 중 index로 조회", protocols = "http")
    ResponseEntity getSubProblemByCodeAndId(@PathVariable("code") String code, @PathVariable("id") Long id);

    @PostMapping(value = "api/v1/sub-problem", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "sub-problem 생성", protocols = "http")
    ResponseEntity saveSubProblemByCode(@RequestBody subProblem subProblem);
}
