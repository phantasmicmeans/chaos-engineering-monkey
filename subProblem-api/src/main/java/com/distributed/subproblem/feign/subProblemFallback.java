package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import feign.hystrix.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class subProblemFallback implements FallbackFactory<subProblemClient> {
    @Override
    public subProblemClient create(Throwable throwable) {
        return new subProblemClient() {
            @Override
            public ResponseEntity getSubProblemById(Long id) {
                return null;
            }

            @Override
            public ResponseEntity getSubProblemByCode(String code) {
                return null;
            }

            @Override
            public ResponseEntity getSubProblemByCodeAndId(String code, Long id) {
                return null;
            }

            @Override
            public ResponseEntity saveSubProblemByCode(subProblem subProblem) {
                return null;
            }

        };
    }
}
