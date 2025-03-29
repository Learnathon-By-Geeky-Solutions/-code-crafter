package com.xenon.core.domain.response.user;

import com.xenon.data.entity.location.Upazila;
import com.xenon.data.entity.user.AccountStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private UserRole role;
    private AccountStatus status;
    private Upazila upazila;
    private String area;
    private Double latitude;
    private Double longitude;
    private Gender gender;
}
