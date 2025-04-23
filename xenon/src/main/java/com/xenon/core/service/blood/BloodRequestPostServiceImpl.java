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
import java.util.Map;
import java.util.stream.Collectors;

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
                        recentPosts.stream().map(BloodRequestPost::toResponse).toList()
                ));
    }

    @Override
    public ResponseEntity<?> getBloodRequestPostPage() {
        try {
            // 1. Get post entity
            BloodRequestPost post = bloodRequestPostRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new ClientException("No blood request found"));

            // 2. Get response count (via native projection)
            int responseCount = bloodRequestPostRepository.getResponseCount(post.getId());

            // 3. Get comments
            List<BloodCommentTable> comments = bloodCommentRepository.findAll();
            List<BloodRequestPostCommentResponse> commentResponses = comments.stream().map(comment ->
                    new BloodRequestPostCommentResponse(
                            comment.getUser().getFastName(),
                            comment.getUser().getLastName(),
                            comment.getComment()
                    )).toList();

            // 4. Build response DTO
            List<BloodRequestPostResponse> postResponseList = List.of(post.toResponse());
            BloodRequestPostDisplayResponse finalResponse = new BloodRequestPostDisplayResponse(
                    responseCount,
                    postResponseList,
                    commentResponses
            );

            return success("Post details retrieved", finalResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBloodPostPage() {
        try {

            List<BloodRequestPost> posts = bloodRequestPostRepository.findAllByUser_Id(getCurrentUser().getId());


            // 2. Get all comments related to these posts
            List<Long> postIds = posts.stream().map(BloodRequestPost::getId).toList();
            List<BloodCommentTable> comments = bloodCommentRepository.findByBloodRequestPostIdIn(postIds);

            // 3. Group comments by postId
            Map<Long, List<BloodCommentTable>> commentMap = comments.stream()
                    .collect(Collectors.groupingBy(c -> c.getBloodRequestPost().getId()));

            // 4. Build response list
            List<BloodRequestPostDisplayResponse> postResponseList = posts.stream().map(post -> {
                List<BloodCommentTable> postComments = commentMap.getOrDefault(post.getId(), List.of());

                List<BloodRequestPostCommentResponse> commentResponses = postComments.stream().map(comment ->
                        new BloodRequestPostCommentResponse(
                                comment.getUser().getFastName(),  // fix typo from 'FastName'
                                comment.getUser().getLastName(),
                                comment.getComment()
                        )).toList();

                int responseCount = postComments.size();

                return new BloodRequestPostDisplayResponse(
                        responseCount,
                        List.of(post.toResponse()), // can also just return one element if you want
                        commentResponses
                );
            }).toList();

            return success("Post details retrieved", postResponseList);
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
}

