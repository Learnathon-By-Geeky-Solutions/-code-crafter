package com.xenon.core.domain.request.blood;

import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBloodRequestPost {


    private String patientName;
    private BloodType bloodType;
    private Integer quantity;
    private String hospitalName;
    private String contactNumber;
    private String description;
    private LocalDate date;
    private Long upazilaId;

    public BloodRequestPost toEntity(User user, Upazila upazila) {
        return new BloodRequestPost(user, upazila, patientName, bloodType, quantity, hospitalName, description, date, contactNumber);
    }
}
