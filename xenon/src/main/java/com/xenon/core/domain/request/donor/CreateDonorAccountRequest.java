package com.xenon.core.domain.request.donor;

import com.xenon.data.entity.donor.BloodType;
import com.xenon.data.entity.donor.Donor;
import com.xenon.data.entity.donor.Interested;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDonorAccountRequest {

    private BloodType bloodType;
    private Integer age;
    private Integer weight;
    private Interested interested;

    public Donor toEntity(User user) {
        return new Donor(bloodType, age, weight, interested, user);
    }
}
