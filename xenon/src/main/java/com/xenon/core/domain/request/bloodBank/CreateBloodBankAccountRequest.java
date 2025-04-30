package com.xenon.core.domain.request.bloodBank;

import com.xenon.data.entity.bloodBank.BloodBank;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBloodBankAccountRequest {
    private String registration_number;

    public BloodBank toEntity(User user) {
        return new BloodBank(this.registration_number, user);
    }
}
