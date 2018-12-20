package com.distributed.problem.rest;

import com.distributed.problem.domain.Problem;
import com.distributed.problem.dto.problemDTO;
import com.distributed.problem.exception.DataInvalidException;
import com.distributed.problem.feign.subProblem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProblemValidator {

    public boolean checkIsEmpty(Object object){
        if(object.getClass().equals(String.class)) { //code - null, length check
            String code = (String) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {code} in uri or json data"));
            if(code.length() !=6 ) throw new DataInvalidException("path variable {code} length must be more than 6");
            return true;

        }else if(object.getClass().equals(Long.class)){ //id - null check
            Long Id = (Long) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your {id} in uri or json data"));
            return true;

        }else if(object.getClass().equals(subProblem.class)){ //POST data
            subProblem subProblem = (subProblem) Optional.ofNullable(object).orElseThrow(()
                    -> new DataInvalidException("please check your json(post) data"));
            return checkIsEmpty(subProblem.getCode()) && !subProblem.getContent().isEmpty();
        }
        return false;
    }

    public List<problemDTO> makeSetOfProAndSubPro(List <Problem> problems, List <subProblem> json_objects) throws Exception{

        List<subProblem> list;
        List<problemDTO> listDTO = new ArrayList<>();

        int k = 0;
        for(Problem pro : problems){
            list = new ArrayList<>(); // add할 json
            String hashcode = pro.getPro_hash(); //hash값
            for(int j = k; j < json_objects.size(); j++){
                subProblem sub = json_objects.get(j);
                if(hashcode.equals(sub.getPro_hash())){ //hash 같으면 problem에 이어 붙어야함.
                    list.add(sub);
                    k++; //k올리고
                }
            }
            problemDTO problemDto = problemDTO.builder()
                    .title(pro.getTitle())
                    .code(pro.getCode())
                    .subProblemList(list).build();
            listDTO.add(problemDto);
        }
        return listDTO;
    }

}
