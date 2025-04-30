package com.xenon.core.domain.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
