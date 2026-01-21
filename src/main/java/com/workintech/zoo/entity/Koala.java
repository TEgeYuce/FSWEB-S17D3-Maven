package com.workintech.zoo.entity;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Koala {
    private Integer id;
    private String name;
    private Double weight;
    private Double sleepHour;
    private String gender;

}
