package com.distributed.subproblem.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class subProblem {

    @Id @GeneratedValue
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String pro_hash;

    @NotEmpty
    @Column(nullable = false)
    private String code;

    @NotEmpty
    @Column(nullable = false)
    private String content;

    private Integer count = 0;

    private LocalDateTime createdTimeAt = LocalDateTime.now();
    private LocalDate createdDateAt = LocalDate.now();

    public subProblem(String code, String content){
        this.code = code;
        this.content = content;
    }
}
