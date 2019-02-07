package dev.distributed.eureka.EurekClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class EurekaValidator {

    boolean checkEmpty(Set regions) {
        return !regions.isEmpty();
    }
    boolean checkPathVariable(String path) {
        return (path.isEmpty() || toString().length() < 3);
    }
    boolean checkInstanceInfoIsEmpty(Application application){
        return application.size() > 0;
    }

    String getInstanaceInfosDetail(List<InstanceInfo> instanceInfos) {
        class InstanceInfoDetail {};
        return "";
    }

}
