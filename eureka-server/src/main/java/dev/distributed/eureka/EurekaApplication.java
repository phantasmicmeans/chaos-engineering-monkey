package dev.distributed.eureka;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
public class EurekaApplication {

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }


    @Bean
    @Primary
    @Profile("develop")
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
