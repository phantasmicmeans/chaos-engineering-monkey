package com.distributed.subproblem.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class subProblem {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String content;

    private Integer count = 0;

    private LocalDateTime createdTimeAt = LocalDateTime.now();
    private LocalDate createdDateAt = LocalDate.now();
}
