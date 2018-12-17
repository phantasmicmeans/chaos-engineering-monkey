package com.distributed.problem.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @EqualsAndHashCode(of= "id") @Builder
public class Problem {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false) //entity 단
    private String title;

    @Column(nullable = false)
    private String code;

    private LocalDate localDate = LocalDate.now();

    private LocalDateTime localDateTime = LocalDateTime.now();
}
