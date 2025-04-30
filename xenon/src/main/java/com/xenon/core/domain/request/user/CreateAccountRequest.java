package com.xenon.core.domain.request.user;

import com.xenon.data.entity.user.User;
import com.xenon.data.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String phone;
    private String password;
    private String confirmPassword;

    public User toEntity(@NonNull PasswordEncoder passwordEncoder, UserRole role) {
        return new User(phone, passwordEncoder.encode(this.password), role);
    }
}
