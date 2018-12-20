package com.distributed.subproblem.repository;

import com.distributed.subproblem.domain.subProblem;
import com.distributed.subproblem.exception.ResourceNotFoundException;
import javassist.NotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class subProblemRepositoryTests {
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Autowired
//    com.distributed.subproblem.feign.subProblemService subProblemService;
//
//    @Test
//    public void createSubProblem() {
//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDate localDate = LocalDate.now();
//
//        String code = "abcde";
//        subProblem problem = subProblem.builder()
//                .code("abcde")
//                .pro_hash(String.valueOf(code.hashCode()))
//                .content("hello word")
//                .count(0)
//                .createdTimeAt(localDateTime)
//                .createdDateAt(localDate)
//                .build();
//
//        this.subProblemService.saveSubProblem(problem);
//
//        Optional<subProblem> problemLoad = this.subProblemService.loadSubProblemById(problem.getId());
//        assertThat(problemLoad.get()).isEqualTo(problem);
//        this.subProblemService.deleteAll();
//    }
//
//    @Test
//    public void findById(){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDate localDate = LocalDate.now();
//
//        String code = "abcdef";
//        subProblem problem = subProblem.builder()
//                .id(1)
//                .code(code)
//                .pro_hash(String.valueOf(code.hashCode()))
//                .content("hello word")
//                .count(0)
//                .createdTimeAt(localDateTime)
//                .createdDateAt(localDate)
//                .build();
//
//        this.subProblemService.saveSubProblem(problem);
//
//        subProblem problemLoad = this.subProblemService.loadSubProblemById(1)
//                .orElseThrow(() -> new ResourceNotFoundException("not found"));
//        assertThat(problemLoad).isEqualTo(problem);
//    }
//    @Test
//    public void findByCodeOrderByIdAsc() {
//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDate localDate = LocalDate.now();
//
//        String code = "abcdeg";
//        subProblem problem = subProblem.builder()
//                .code(code)
//                .pro_hash(String.valueOf(code.hashCode()))
//                .content("hello word")
//                .count(0)
//                .createdTimeAt(localDateTime)
//                .createdDateAt(localDate)
//                .build();
//
//        this.subProblemService.saveSubProblem(problem);
//
//        List<subProblem> list = this.subProblemService.loadSubProblemByCode("abcdeg");
//        assertThat(!list.isEmpty()).isTrue();
//        assertThat(list.size()).isEqualTo(1);
//
//        subProblem problemLoad = list.get(0);
//        assertThat(problemLoad).isEqualTo(problem);
//    }
}
