package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.DataInvalidException;
import com.distributed.subproblem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class subProblemValidator {

    public boolean checkIsEmpty(Object object){

        if(object instanceof String) {
            String code = (String) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {code} in uri or json data"));

            String message = "length must be 6, please check your {code}";

            if(code.length() < 6) { //code length validation
                message = "code length is short. " + message;
            }else if(code.length() > 6) {
                message = "code length is long. " + message;
            }else // length equals 6
                return true;

            throw new DataInvalidException(message);
        }
        else if(object instanceof Integer) {
            Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {id} in uri or json data"));
            return true;
        }
        else if(object instanceof subProblem) {
            subProblem subProblem = (subProblem) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your json(post) data"));

            if(subProblem.getContent() == null)
                throw new DataInvalidException("content must not be null");

            return checkIsEmpty(subProblem.getCode());
        }
        else if(object instanceof List) {
            if(((List) object).isEmpty() || ((List) object).size() == 0)
                throw new ResourceNotFoundException("cannot found sub-problem list with that code");

            return true;
        }
        return false;
    }

}
