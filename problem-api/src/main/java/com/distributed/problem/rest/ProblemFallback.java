package com.distributed.problem.rest;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProblemFallback implements FallbackFactory<ProblemController> {
    @Override
    public ProblemController create(Throwable throwable) {
        return null;
    }
}
