package com.xenon.core.service.blood;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blood.CreateBloodRequestPost;
import com.xenon.core.domain.response.blood.BloodDashBoardResponse;
import com.xenon.core.domain.response.blood.BloodRequestPostCommentResponse;
import com.xenon.core.domain.response.blood.BloodRequestPostDisplayResponse;
import com.xenon.core.domain.response.blood.BloodRequestPostResponse;
import com.xenon.core.domain.response.blood.projection.BloodMetaDataProjection;
import com.xenon.core.service.BaseService;
import com.xenon.data.entity.blood.BloodCommentTable;
import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.entity.location.Upazila;
import com.xenon.data.repository.BloodCommentRepository;
import com.xenon.data.repository.BloodRequestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BloodRequestPostServiceImpl extends BaseService implements BloodRequestPostService {

    private final BloodRequestPostRepository bloodRequestPostRepository;
    private final BloodCommentRepository bloodCommentRepository;

    @Override
    public ResponseEntity<?> createBloodRequestPost(CreateBloodRequestPost body) {

        validateCreateBloodRequestPost(body);
        Upazila upazila = upazilaRepository.findById(body.getUpazilaId()).orElseThrow(() -> new ClientException("No upazila Found"));

        try {
            bloodRequestPostRepository.save(body.toEntity(getCurrentUser(), upazila));
            return success("Request created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBloodDashboard() {
        BloodMetaDataProjection bloodMetaDataProjection = bloodRequestPostRepository.getBloodMetadata();

        List<BloodRequestPost> recentPosts = bloodRequestPostRepository.findRecentPosts(5);

        return success("Dashboard retrieved successfully",
                new BloodDashBoardResponse(
                        bloodMetaDataProjection.getTotalDonor(),
                        bloodMetaDataProjection.getTotalDonation(),
                        bloodMetaDataProjection.getTotalPost(),
                        recentPosts.stream().map(item -> item.toResponse(null)).toList()
                ));
    }

    @Override
    public ResponseEntity<?> getBloodRequestPostPage() {
        try {
            List<BloodRequestPost> posts = bloodRequestPostRepository.findAll();
            List<BloodCommentTable> comments = bloodCommentRepository.findAll();

            return success("Post details retrieved", getBloodRequestPostDisplayResponse(posts, comments));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBloodPostPage() {
        try {
            List<BloodRequestPost> posts = bloodRequestPostRepository.findAllByUser_Id(getCurrentUser().getId());
            List<Long> postIds = posts.stream().map(BloodRequestPost::getId).toList();
            List<BloodCommentTable> comments = bloodCommentRepository.findByBloodRequestPostIdIn(postIds);

            return success("Post details retrieved", getBloodRequestPostDisplayResponse(posts, comments));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void validateCreateBloodRequestPost(CreateBloodRequestPost body) {
        super.validateBody(body);

        if (isNullOrBlank(body.getPatientName())) throw requiredField("Patient Name");
        if (body.getBloodType() == null) throw requiredField("Blood Type");
        if (body.getQuantity() == null) throw requiredField("Quantity");
        if (isNullOrBlank(body.getHospitalName())) throw requiredField("Hospital Name");
        if (isNullOrBlank(body.getContactNumber())) throw requiredField("Contact Number");
        if (isNullOrBlank(body.getDescription())) throw requiredField("Description");

        if (!isValidNumber(body.getQuantity().toString())) throw clientException("Use only number for quantity");
        if (!PHONE_PATTERN.matcher(body.getContactNumber()).matches()) throw clientException("Invalid phone number");
        if (body.getDate() == null) throw requiredField("Date");

    }

    private BloodRequestPostDisplayResponse getBloodRequestPostDisplayResponse(List<BloodRequestPost> posts, List<BloodCommentTable> comments) {
        List<BloodRequestPostResponse> bloodRequestPostResponseList = posts
                .stream()
                .map(item -> item.toResponse(comments.stream()
                        .filter(comment -> comment.getBloodRequestPost().getId().equals(item.getId()))
                        .map(comment2 -> new BloodRequestPostCommentResponse(
                                comment2.getUser().getFirstName(),
                                comment2.getUser().getLastName(),
                                comment2.getComment()
                        )).toList())).toList();
        return new BloodRequestPostDisplayResponse(
                comments.size(),
                bloodRequestPostResponseList
        );
    }
}

