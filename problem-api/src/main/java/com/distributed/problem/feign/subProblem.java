package com.distributed.problem.feign;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class subProblem {

    @NotEmpty
    private Integer id;
    @NotEmpty
    private String code;
    @NotEmpty
    private String pro_hash;
    @NotEmpty
    private String content;
    @NotNull
    private LocalDate localDate = LocalDate.now();
    @NotNull
    private LocalDateTime localDateTime = LocalDateTime.now();
}
