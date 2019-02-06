package dev.distributed.eureka.EurekClient;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EurekaValidator {

    public boolean checkEmpty(Set regions) {
        return regions.isEmpty();
    }

    public boolean checkPathVariable(String path) {
        return (path.isEmpty() || toString().length() < 3);
    }
}
