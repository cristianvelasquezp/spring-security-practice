package org.cristianvelasquezp.springsecuritypractice.models;

import lombok.Data;

@Data
public class UserErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
