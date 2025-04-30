package com.xenon.core.domain.response.blood;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestPostCommentResponse {

    private String userFirstName;
    private String userLastName;
    private String content;
}
