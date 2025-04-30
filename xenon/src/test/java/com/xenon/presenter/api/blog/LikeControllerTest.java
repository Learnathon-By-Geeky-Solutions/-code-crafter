package com.xenon.presenter.api.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.blog.like.LikeStatus;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.blog.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final Long BLOG_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void toggleLike_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        LikeStatus likeStatus = new LikeStatus(true, 5);
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog liked successfully", likeStatus);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(likeService).toggleLike(anyLong());

        // Act & Assert
        mockMvc.perform(post("/api/v1/like/create/blogs/{blogId}/like", BLOG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog liked successfully"))
                .andExpect(jsonPath("$.data.liked").value(true))
                .andExpect(jsonPath("$.data.likeCount").value(5));

        verify(likeService).toggleLike(eq(BLOG_ID));
    }

    @Test
    void getUserLikedBlogs_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 5, 1);
        
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "User liked blogs retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);
        
        doReturn(responseEntity).when(likeService).getUserLikedBlogs(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/like/user/liked-blogs")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "createdAt")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("User liked blogs retrieved successfully"))
                .andExpect(jsonPath("$.data.pageNumber").value(0))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.totalElements").value(5))
                .andExpect(jsonPath("$.data.totalPages").value(1));

        verify(likeService).getUserLikedBlogs(any(Pageable.class));
    }
}