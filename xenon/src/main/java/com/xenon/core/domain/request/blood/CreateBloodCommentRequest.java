package com.xenon.core.domain.request.blood;

import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.blood.BloodCommentTable;
import com.xenon.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBloodCommentRequest {

    private Long postId;
    private String comment;

    public BloodCommentTable toEntity(User user, BloodRequestPost bloodRequestPost) {
        return new BloodCommentTable(user, bloodRequestPost, comment);
    }
}
