package com.xenon.core.domain.request.user;

import com.xenon.data.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequest {
    private String fastName;
    private String lastName;
    private String email;
    private Long upazilaId;
    private Gender gender;
    private String area;
}
