package org.cristianvelasquezp.springsecuritypractice.models;

import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String role;
}
