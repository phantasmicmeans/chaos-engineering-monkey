package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.ResourceNotFoundException;
import com.distributed.subproblem.repository.subProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("subProblem")
public class subProblemService {

    @Autowired
    subProblemRepository subProblemRepository;

    public Optional<subProblem> loadSubProblemById(Long Id){
        return subProblemRepository.findById(Id);
    }

    public List<subProblem> loadSubProblemByCode(String code) {
        List<subProblem> subProblems = subProblemRepository.findByCodeOrderByIdAsc(code);
        return !subProblems.isEmpty() ? subProblems : new ArrayList<>();
    }

    public Object saveSubProblem(subProblem subProblem){
        try {
            subProblemRepository.save(subProblem);
            return subProblem;
        }catch(Exception e){
            return e.toString();
        }
    }

    public void deleteAll(){
        this.subProblemRepository.deleteAll();
    }
}
