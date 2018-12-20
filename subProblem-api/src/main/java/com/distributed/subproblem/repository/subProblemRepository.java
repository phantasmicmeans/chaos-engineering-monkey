package com.distributed.subproblem.repository;

import com.distributed.subproblem.domain.subProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface subProblemRepository extends JpaRepository <subProblem, Integer> {
    List<subProblem> findByCodeOrderByCode(final String code);
}
