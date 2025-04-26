// Response with health authority minimal information
package com.xenon.core.domain.response.healthAuthorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthAuthorityResponse {
    private Long id;
    private String name; // From user entity (first name + last name)
    private String contactInfo; // Optional field for authority contact
}