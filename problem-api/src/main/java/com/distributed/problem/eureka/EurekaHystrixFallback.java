package com.distributed.problem.eureka;

import feign.hystrix.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class EurekaHystrixFallback implements FallbackFactory<ClientEureka> {
    @Override
    public ClientEureka create(Throwable throwable) {
        return new ClientEureka() {
            @Override
            public ResponseEntity getDetailMyClientFromEureka() {
                return null;
            }

            @Override
            public ResponseEntity getApplicationsFromEureka() {
                return null;
            }

            @Override
            public ResponseEntity getApplicationFromEureka(String applicationName) {
                return null;
            }

            @Override
            public ResponseEntity getApplicationDetailFromEureka(String applicationName) {
                return null;
            }

            @Override
            public ResponseEntity getAnyRegionsFromEureka() {
                return null;
            }

            @Override
            public ResponseEntity getApplicationsForRegions(String regions) {
                return null;
            }
        };
    }
}
