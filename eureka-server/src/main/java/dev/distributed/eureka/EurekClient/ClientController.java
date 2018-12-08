package dev.distributed.eureka.EurekClient;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.sun.jersey.api.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.TreeSet;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "eureka/")
public class ClientController {

    private DiscoveryClient discoveryClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "regions/clients", method = GET) //eureka server에 등록된 regions
    public String getClientList(){

        TreeSet<String> regions = new TreeSet<>(discoveryClient.getAllKnownRegions());
        StringBuilder sb = new StringBuilder();

        if(checkEmpty(regions)) throw new NotFoundException("No Regions");
        regions.forEach(sb::append);
        return sb.toString();
    }

    @RequestMapping(value = "clients/applications/{applicationName}", method = GET)
    public String getApplications(@Valid @PathVariable("applicationName") String applicationName){

        if(checkPathVariable(applicationName)) throw new ValidationException("Please Check your applicationName");

        Application application = discoveryClient.getApplication(applicationName);
        return application.getName().isEmpty()? "cannot found application" : application.getName();
    }

    @RequestMapping(value = "regions/{regions}/clients/applications", method = GET)
    public String getAppicationsForRegions(@Valid @PathVariable("regions") String regions){

        StringBuilder sb = new StringBuilder();
        Applications applications = discoveryClient.getApplicationsForARegion(regions);
        if(applications.size()==0) return "cannot found applications for region : " + regions;
        applications.getRegisteredApplications().forEach(sb::append);

        return sb.toString();
    }

    private boolean checkEmpty(Set regions){
        return regions.isEmpty();
    }

    private boolean checkPathVariable(String str){
        return (str.isEmpty() || str.length() < 3);
    }

}
