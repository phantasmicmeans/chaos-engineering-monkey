package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.DataInvalidException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class subProblemValidator {

    public boolean checkIsEmpty(Object object){

        if(object instanceof String) {
            String code = (String) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {code} in uri or json data"));
            return code.length() == 6;
        }
        else if(object instanceof Integer) {
            Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {id} in uri or json data"));
            return true;
        }
        else if(object instanceof subProblem) {
            subProblem subProblem = (subProblem) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your json(post) data"));
            return checkIsEmpty(subProblem.getCode()) && subProblem.getContent() != null;
        }
        return false;
    }

}
