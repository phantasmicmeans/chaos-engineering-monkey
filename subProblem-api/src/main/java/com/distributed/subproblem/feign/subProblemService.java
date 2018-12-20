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

    public Optional<subProblem> loadSubProblemById(Integer Id){
        return subProblemRepository.findById(Id);
    }

    public List<subProblem> loadSubProblemByCode(String code) {
        List<subProblem> subProblems = subProblemRepository.findByCodeOrderByCode(code);
        return !subProblems.isEmpty() ? subProblems : new ArrayList<>();
    }

    public Object saveSubProblem(subProblem subProblem){
        try {
            subProblemRepository.save(subProblem);
            return subProblem;
        }catch(Throwable e){
            return e.getCause().toString();
        }
    }

    public Object saveSubProblems(List<subProblem> subProblems){
        try{
            return this.subProblemRepository.saveAll(subProblems);
        }catch(Throwable e){
            return e.getCause().toString();
        }
    }

    public void deleteAll(){
        this.subProblemRepository.deleteAll();
    }
}
