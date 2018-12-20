package com.distributed.problem.eureka;

import com.distributed.problem.exception.DataInvalidException;
import com.distributed.problem.exception.ResourceNotFoundException;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class EurekaController implements ClientEureka{

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private EurekaInstance eurekaInstance;

    @Value("${spring.application.name}")
    private String clientName;

    @Value("${eureka.instance.instance-id")
    private String instanceId;

    @Override
    public ResponseEntity getDetailMyClientFromEureka() { //(o)

        Application application = Optional.ofNullable(eurekaClient.getApplication(this.clientName))
                .orElseThrow(()-> new ResourceNotFoundException("No data with spring.application.name"));
        StringBuilder sb = new StringBuilder();
        application.getInstances().forEach(sb::append);
        return ResponseEntity.ok(sb.toString());
    }

    @Override
    public ResponseEntity getApplicationsFromEureka() { //(o)
        //resoureNotFoundException is custom Exception with HttpStatus 404
        List<Application> applications = Optional.ofNullable(eurekaClient.getApplications())
                .orElseThrow(() -> new ResourceNotFoundException("No data")) //Http 404
                .getRegisteredApplications();

        StringBuilder sb = new StringBuilder();
        applications.forEach(sb::append);
        return ResponseEntity.ok(sb.toString());
    }

    @Override
    public ResponseEntity getApplicationFromEureka(String applicationName) { //(o)

        if(checkPathVariable(applicationName)) throw new DataInvalidException("Data invalid, Please Check your data");

        Application application = Optional.ofNullable(eurekaClient.getApplication(applicationName))
                .orElseThrow(()-> new ResourceNotFoundException("No Applications with that name"));
        eurekaInstance.setEurekaInstances(application.getInstances());
        return ResponseEntity.ok(eurekaInstance.simpleInstanceInfo());
    }

    @Override
    public ResponseEntity getApplicationDetailFromEureka(String applicationName) { //(o)
        //자세히
        if(checkPathVariable(instanceId)) throw new DataInvalidException("Data invalid, Please Check your data");

        Application application = Optional.ofNullable(eurekaClient.getApplication(applicationName))
                .orElseThrow(() -> new ResourceNotFoundException("No Application with that name"));

        eurekaInstance.setEurekaInstances(application.getInstances());
        return ResponseEntity.ok(eurekaInstance.InstancesInfo());
    }

    @Override
    public ResponseEntity getAnyRegionsFromEureka() { // (o)
        //모든 리전 명세
        Set<String> regions = Optional.of(eurekaClient.getAllKnownRegions())
                .orElseThrow(() -> new ResourceNotFoundException("No Regions"));

        StringBuilder sb = new StringBuilder();
        regions.forEach(sb::append);
        return ResponseEntity.ok(sb);
    }

    @Override
    public ResponseEntity getApplicationsForRegions(String regions) { //(o)

        if(checkPathVariable(regions)) throw new DataInvalidException("Data invalid, Please Check your data");

        Applications applications = Optional.ofNullable(eurekaClient.getApplicationsForARegion(regions))
                .orElseThrow(() -> new ResourceNotFoundException("No Application with that regions"));

        StringBuilder sb = new StringBuilder();
        applications.getRegisteredApplications().forEach(sb::append);
        return ResponseEntity.ok(sb);
    }

    private boolean checkPathVariable(String str){
        return (str.isEmpty() || str.length() < 3);
    }
}
