package dev.distributed.eureka.EurekClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.sun.jersey.api.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "eureka/")
public class ClientController {

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private EurekaValidator eurekaValidator;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "regions/clients", method = GET) //eureka server에 등록된 regions
    public ResponseEntity getClientList() {

        TreeSet<String> regions = new TreeSet<>(discoveryClient.getAllKnownRegions());
        StringBuilder sb = new StringBuilder();

        Iterator<String> iterator = regions.descendingIterator();

        int idx = 0;
        while(iterator.hasNext()){
            logger.info("idx : " + idx + iterator.next());
            idx ++;
        }

        if (eurekaValidator.checkEmpty(regions)) throw new NotFoundException("No Regions");
        regions.forEach(sb::append);
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "clients/applications/{applicationName}", method = GET)
    public String getApplications(@Valid @PathVariable("applicationName") String applicationName) {
    //Application Name이 존재하는지 확인, 존재하면 이름 return
        if (eurekaValidator.checkPathVariable(applicationName)) throw new ValidationException("Please Check your applicationName");

        Application application = discoveryClient.getApplication(applicationName);
        return application.getName().isEmpty() ? "cannot found application" : application.getName();
    }

    @RequestMapping(value = "clients/applications/{applicationName}/detail", method = GET)
    public String getApplicationsDetailWithName(@Valid @PathVariable("applicationName") String applicationName) throws Exception {
        //위 getApplications로 Name 존재 유무 확인, Name존재 시 이 api 호출 -> detail전부
        if(eurekaValidator.checkPathVariable(applicationName)) throw new ValidationException("Please Check your application Name");
        StringBuilder sb = new StringBuilder();

        Application application = this.discoveryClient.getApplication(applicationName);
        if(this.eurekaValidator.checkInstanceInfoIsEmpty(application)) {
            List<InstanceInfo> instanceInfos = application.getInstances();

            Iterator<InstanceInfo> iterator = instanceInfos.iterator();
            while(iterator.hasNext()) {
                sb.append(objectMapper.writeValueAsString(iterator.next()));
            }

            return sb.toString();
        }
        return "cannot found any application";
    }

    @RequestMapping(value = "regions/{regions}/clients/applications", method = GET)
    public String getApplicationsForRegions(@Valid @PathVariable("regions") String regions) {
    //eureka server에 포함된 application들의 regions 종류 명
        StringBuilder sb = new StringBuilder();
        Applications applications = discoveryClient.getApplicationsForARegion(regions);
        if (applications.size() == 0) return "cannot found applications for region : " + regions;
        applications.getRegisteredApplications().forEach(sb::append);

        return sb.toString();
    }
}

