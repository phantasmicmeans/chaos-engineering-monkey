package com.distributed.problem.eureka;


import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ClientEureka {

    @GetMapping(value = "/eureka/actuator/info")
    @ApiOperation(value = "eureka로 부터 현재 client의 name을 이용해 client의 간단 정보 조회", protocols = "http")
    ResponseEntity getDetailMyClientFromEureka(); //필요한 것들만 모아서 출력 시킴, 자기 자신

    @GetMapping(value = "/eureka/applications/info") //(o) 유레카에 등록된 모든 어플리케이션 - 명세는 간단히
    @ApiOperation(value = "eureka에 등록된 모든 application의 간단 정보 조회", protocols = "http")
    ResponseEntity getApplicationsFromEureka();

    @GetMapping(value = "/eureka/application/{applicationName}/info", produces = "application/json") // (o) applications들 중 이름으로 application 명세, 간단히
    @ApiOperation(value = "applicationName이 일치하는 application들 간단 정보 조회", protocols = "http")
    ResponseEntity getApplicationFromEureka(@PathVariable String applicationName);
    //AppName, Host, Id, IpAddr, Status

    @GetMapping(value = "/eureka/applications/{applicationName}/info/detail", produces = "application/json") //eureka instanceId로 명세 찾기
    @ApiOperation(value = "applicationName이 일치하는 application의 자세한 정보 조회", protocols = "http")
    ResponseEntity getApplicationDetailFromEureka(@PathVariable("applicationName") String applicationName);
    //applicationName으로 모든 data

    @GetMapping(value = "/eureka/regions/info", produces = "application/json")
    @ApiOperation(value = "eureka로 부터 사용중인 모든 region 조회", protocols = "http")
    ResponseEntity getAnyRegionsFromEureka();
    // 모든 리전 명세

    @GetMapping(value = "/eureka/regions/{regions}/applications/info", produces = "application/json")
    @ApiOperation(value = "region을 이용해 eureka로부터 해당 region에 담겨있는 application 간단 정보 조회")
    ResponseEntity getApplicationsForRegions(@PathVariable("regions") String regions); //region을 이용해 application 명세
}
