package com.distributed.subproblem.feign;

import org.springframework.context.annotation.Configuration;

@Configuration
public class subProblemConfiguration {

/*
    Spring Cloud Netflix provides the following beans by default for feign (BeanType beanName: ClassName):

    Decoder feignDecoder: ResponseEntityDecoder (which wraps a SpringDecoder)
    Encoder feignEncoder: SpringEncoder
    Logger feignLogger: Slf4jLogger
    Contract feignContract: SpringMvcContract
    Feign.Builder feignBuilder: HystrixFeign.Builder
    Client feignClient: if Ribbon is enabled it is a LoadBalancerFeignClient, otherwise the default feign client is used

    Spring Cloud Netflix does not provide the following beans by default for feign,
    but still looks up beans of these types from the application context to create the feign client:

    Logger.Level
    Retryer
    ErrorDecoder
    Request.Options
    Collection<RequestInterceptor>
    SetterFactory
    Creating a bean of one of those type and placing it in a @FeignClient configuration
 */
}