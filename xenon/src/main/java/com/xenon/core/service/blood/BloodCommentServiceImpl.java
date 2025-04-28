package com.xenon.core.service.blood;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.request.blood.CreateBloodCommentRequest;
import com.xenon.core.service.common.BaseService;
import com.xenon.data.entity.blood.BloodRequestPost;
import com.xenon.data.repository.BloodRequestPostRepository;
import com.xenon.data.repository.BloodCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BloodCommentServiceImpl extends BaseService implements BloodCommentService {

    private final BloodRequestPostRepository bloodRequestPostRepository;
    private final BloodCommentRepository bloodCommentRepository;

    @Override
    public ResponseEntity<?> createBloodCommentRequest(CreateBloodCommentRequest body) {
        validateCreateBloodResponseRequest(body);

        BloodRequestPost post = bloodRequestPostRepository.findById(body.getPostId()).orElseThrow(() -> new ClientException("No post Found!"));

        try {
            bloodCommentRepository.save(body.toEntity(getCurrentUser(), post));
            return success("Response created successfully", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(e);
        }
    }




    private void validateCreateBloodResponseRequest(CreateBloodCommentRequest body) {
        super.validateBody(body);

        if (body.getPostId() == null) throw requiredField("Post Id");
        if (isNullOrBlank(body.getComment())) throw requiredField("No comment Posts");

    }
}
