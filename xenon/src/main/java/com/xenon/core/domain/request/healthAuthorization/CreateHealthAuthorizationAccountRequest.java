package com.xenon.core.domain.request.healthAuthorization;

import com.xenon.data.entity.healthAuthorization.HealthAuthorization;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHealthAuthorizationAccountRequest {

    private String authorizationNumber;

    public HealthAuthorization toEntity(User user) {
        return new HealthAuthorization(authorizationNumber, user);
    }
}
