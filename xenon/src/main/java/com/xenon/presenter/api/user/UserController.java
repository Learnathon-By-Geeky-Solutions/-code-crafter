package com.xenon.presenter.api.user;

import com.xenon.common.annotation.PreAuthorize;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.domain.request.user.UpdateAccountRequest;
import com.xenon.core.domain.request.user.UpdateUserLatitudeLongitude;
import com.xenon.core.service.user.UserService;
import com.xenon.data.entity.user.UserRole;
import com.xenon.presenter.config.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {SecurityConfiguration.BACKEND_URL, SecurityConfiguration.FRONTEND_URL})
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> createAccount(@Nullable
    @RequestParam UserRole role, @RequestBody CreateAccountRequest body) {
        return userService.createAccount(body, role);
    }


    @PutMapping("user-profile-update")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateProfile(@Nullable  @RequestBody UpdateAccountRequest body) {
        return userService.update(body);
    }

    @PutMapping("user-profile-latitude-longitude-update")
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateLatitudeLongitude(@Nullable  @RequestBody UpdateUserLatitudeLongitude body) {
        return userService.updateLatitudeLongitude(body);
    }


    @GetMapping
    @PreAuthorize(shouldCheckAccountStatus = true)
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUserProfile() {
        return userService.getProfile();
    }

}
