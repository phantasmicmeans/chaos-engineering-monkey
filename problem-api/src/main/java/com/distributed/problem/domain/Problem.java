package com.distributed.problem.domain;


import lombok.*;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor
@AllArgsConstructor @EqualsAndHashCode(of= "id") @Builder @ToString
public class Problem {
//@NotEmpty - null x, "" x, " " o # @NonNull - null x, "" o, @NotBlank - all x
    @Id
    @GeneratedValue
    private Integer id;
    @NotEmpty
    @NaturalId
    private String pro_hash;
    @NonNull
    @Column(nullable = false) //entity 단
    private String title;
    @NonNull
    @Column(nullable = false)
    private String code;
    private LocalDate localDate = LocalDate.now();
    private LocalDateTime localDateTime = LocalDateTime.now();


}
