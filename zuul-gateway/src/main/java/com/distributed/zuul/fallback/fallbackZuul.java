package com.distributed.zuul.fallback;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class fallbackZuul implements FallbackProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getRoute() {
        return "*";
        //you need to specify the route ID the fallback is for.
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) { //return as fallback
        if(cause instanceof HystrixTimeoutException){
            logger.info(cause.getMessage());
            return response(HttpStatus.GATEWAY_TIMEOUT);
        }else
            return response(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private ClientHttpResponse response(final HttpStatus status){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("fallback".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
