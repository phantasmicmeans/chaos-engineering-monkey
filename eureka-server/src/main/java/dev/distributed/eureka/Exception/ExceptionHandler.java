package dev.distributed.eureka.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice //전역처리
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException re)
    {
        ErrorResponse errorResponse = this.getErrorResponse(re);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse getErrorResponse(Exception e){
        return new ErrorResponse(new Date(), e.getMessage());
    }
}
