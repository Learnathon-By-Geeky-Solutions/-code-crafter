package com.xenon.core.domain.request.pharmacy;

import com.xenon.data.entity.pharmacy.Pharmacy;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePharmacyAccountRequest {

    private String tradeLicenseNumber;

    public Pharmacy toEntity(User user) {
        return new Pharmacy(tradeLicenseNumber, user);
    }
}
