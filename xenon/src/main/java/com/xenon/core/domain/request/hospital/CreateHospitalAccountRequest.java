/*
package com.xenon.core.domain.request.hospital;

import com.xenon.data.entity.hospital.HOSPITAL_TYPE;
import com.xenon.data.entity.hospital.Hospital;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHospitalAccountRequest {

    private String registrationNo;
    private HOSPITAL_TYPE hospitalType;

    public Hospital toEntity(User user) {
        return new Hospital(registrationNo, hospitalType, user);
    }
}
*/
