package com.distributed.subproblem.feign;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.ResourceNotFoundException;
import com.distributed.subproblem.repository.subProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("subProblem")
public class subProblemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    subProblemRepository subProblemRepository;

    public Optional<subProblem> loadSubProblemById(Integer Id){
        return subProblemRepository.findById(Id);
    }

    public Optional<List<subProblem>> loadSubProblemByCode(String code) {
        List<subProblem> subProblems = subProblemRepository.findByCodeOrderByCode(code);
        return Optional.ofNullable(subProblems);
        //return !subProblems.isEmpty() ? subProblems : new ArrayList<>();
    }

    public List<subProblem> loadSubProblemBycodePaging(String code, Pageable pageable){
        List<subProblem> subProblems = subProblemRepository.findByCodeOrderByCode(code, pageable);
        logger.info("size : " + subProblems.size() + " ");
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
