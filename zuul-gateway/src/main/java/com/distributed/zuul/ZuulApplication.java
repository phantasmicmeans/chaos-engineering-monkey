package com.distributed.zuul;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@EnableEurekaClient
@EnableHystrixDashboard
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {
//By default Zuul routes all Cross Origin requests (CORS) to the services.

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    @Profile({"ap-northeast-2a","ap-northeast-2c"})
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils){
        EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        b.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
        b.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
        b.setNonSecurePort(port);
        b.setDataCenterInfo(info);
        return b;
    }
}

