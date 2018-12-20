package com.distributed.problem.dto;

import com.distributed.problem.feign.subProblem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@ToString @AllArgsConstructor @Builder
public class problemDTO {
//@NotEmpty - null x, "" x, " " o # @NonNull - null x, "" o, @NotBlank - all x\
    @JsonProperty("title")
    private String title;
    @JsonProperty("code")
    private String code;
    @JsonProperty("local_date")
    private LocalDate localDate = LocalDate.now();
    @JsonProperty("local_date_time")
    private LocalDateTime localDateTime = LocalDateTime.now();
    @JsonProperty("subproblem")
    List<subProblem> subProblemList;

}
