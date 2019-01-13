package com.distributed.subproblem.repository;

import com.distributed.subproblem.domain.subProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface subProblemRepository extends JpaRepository <subProblem, Integer> {
    List<subProblem> findByCodeOrderByCode(final String code);
    List<subProblem> findByCodeOrderByCode(final String code, Pageable pageable);
}
