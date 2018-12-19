package com.distributed.problem.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

            @Override
            public ResponseEntity<String> saveSubProblemsByCode(List<subProblem> subProblemList) {
                return null;
            }
        };
    }
}
