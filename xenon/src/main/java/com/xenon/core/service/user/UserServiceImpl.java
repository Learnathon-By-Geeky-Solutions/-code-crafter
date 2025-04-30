package com.xenon.core.service.user;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.user.CreateAccountRequest;
import com.xenon.core.domain.request.user.UpdateAccountRequest;
import com.xenon.core.domain.request.user.UpdateUserLatitudeLongitude;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.entity.user.User;
import com.xenon.data.entity.user.UserRole;
import com.xenon.data.repository.OfflineAppointmentTableRepository;
import com.xenon.data.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final DoctorScheduleRepository offlineDoctorScheduleRepository;
    private final OfflineAppointmentTableRepository offlineAppointmentTableRepository;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest body, UserRole role) {
        if (role == null) throw requiredField("role");
        validateCreateAccountRequest(body);

        try {
            userRepository.save(body.toEntity(passwordEncoder, role));
            return success("Account created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> update(UpdateAccountRequest body) {
        validateUpdateAccountRequest(body);

        User user = getCurrentUser();

        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setEmail(body.getEmail());
        user.setGender(body.getGender());
        user.setArea(body.getArea());
        Upazila upazila = upazilaRepository.findById(body.getUpazilaId()).orElseThrow(() -> new ClientException("No upazila Found"));
        user.setUpazila(upazila);
        try {
            userRepository.save(user);
            return success("Account updated successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    @Override
    public ResponseEntity<?> getProfile() {
        return success("User profile has been retrieved successfully", getCurrentUser().toResponse());
    }

    @Override
    public ResponseEntity<?> updateLatitudeLongitude(UpdateUserLatitudeLongitude body) {

        User user = getCurrentUser();

        user.setLatitude(body.getLatitude());
        user.setLongitude(body.getLongitude());
        try {
            userRepository.save(user);
            return success("Location updated successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }


    private void validateCreateAccountRequest(CreateAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPhone())) throw requiredField("phone");
        if (!PHONE_PATTERN.matcher(body.getPhone()).matches()) throw clientException("Invalid phone number");
        if (userRepository.existsByPhone(body.getPhone())) throw clientException("Phone number already exists!");
        if (isNullOrBlank(body.getPassword())) throw requiredField("password");
        if (body.getPassword().length() < 6 || body.getPassword().length() > 32)
            throw clientException("Password must be between 6 and 32 characters");
        if (isNullOrBlank(body.getConfirmPassword())) throw requiredField("confirmPassword");
        if (!Objects.equals(body.getPassword(), body.getConfirmPassword()))
            throw clientException("Passwords do not match");
    }

    private void validateUpdateAccountRequest(UpdateAccountRequest body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getFirstName())) throw requiredField("First name");
        if (isNullOrBlank(body.getLastName())) throw requiredField("Last name");
        if (isNullOrBlank(body.getEmail())) throw requiredField("Email");
        if (!EMAIL_PATTERN.matcher(body.getEmail()).matches()) throw clientException("Invalid email");
        if (!Objects.equals(userRepository.findById(getCurrentUser().getId()).map(User::getEmail).orElse(null), body.getEmail()) && userRepository.existsByEmail(body.getEmail()))
            throw clientException("Email already exists!");
        if (isNullOrBlank(String.valueOf(body.getUpazilaId()))) throw requiredField("Location selection");
        if (isNullOrBlank(String.valueOf(body.getGender()))) throw requiredField("Gender ");
        if (isNullOrBlank(body.getArea())) throw requiredField("Area");
    }

}