package com.xenon.presenter.api.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.blog.comment.CreateCommentRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.service.blog.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreateCommentRequest createCommentRequest;
    private final Long BLOG_ID = 1L;
    private final Long COMMENT_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        objectMapper = new ObjectMapper();
        
        // Initialize comment request
        createCommentRequest = new CreateCommentRequest();
        createCommentRequest.setContent("This is a test comment on the blog post");
    }

    @Test
    void createCommentRequest_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Comment created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(commentService).createCommentRequest(anyLong(), any(CreateCommentRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/comment/create/{blogId}/comments", BLOG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCommentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Comment created successfully"));

        verify(commentService).createCommentRequest(eq(BLOG_ID), any(CreateCommentRequest.class));
    }

    @Test
    void updateComment_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Comment updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(commentService).updateComment(anyLong(), any(CreateCommentRequest.class));

        // Update comment content
        createCommentRequest.setContent("This is an updated comment on the blog post");

        // Act & Assert
        mockMvc.perform(put("/api/v1/comment/comments/{commentId}", COMMENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCommentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Comment updated successfully"));

        verify(commentService).updateComment(eq(COMMENT_ID), any(CreateCommentRequest.class));
    }

    @Test
    void deleteComment_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Comment deleted successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(commentService).deleteComment(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/comment/comments/{commentId}", COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Comment deleted successfully"));

        verify(commentService).deleteComment(eq(COMMENT_ID));
    }
}