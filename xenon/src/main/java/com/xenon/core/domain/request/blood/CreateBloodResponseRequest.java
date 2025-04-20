package com.xenon.core.domain.request.blood;

import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.blood.BloodResponse;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBloodResponseRequest {

    private Long postId;
    private String comment;

    public BloodResponse toEntity(User user, BloodRequestPost bloodRequestPost) {
        return new BloodResponse(user, bloodRequestPost, comment);
    }
}
