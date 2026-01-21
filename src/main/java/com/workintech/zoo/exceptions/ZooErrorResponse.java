package com.workintech.zoo.exceptions;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ZooErrorResponse {

    private String message;
    private int status;
    private long timestamp;
}
