package com.distributed.problem.repository;

import com.distributed.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface problemRepository extends JpaRepository <Problem, Integer> {
//
    Collection<Problem> findAllByCode(String code); //code로  찾기
    //Optional<Problem> findByIdAndCode(Integer id, String code); //code, id 로 찾기

}
