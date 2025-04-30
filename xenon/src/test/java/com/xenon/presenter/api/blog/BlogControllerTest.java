package com.xenon.presenter.api.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenon.core.domain.request.blog.BlogPostRequest;
import com.xenon.core.domain.response.BaseResponse;
import com.xenon.core.domain.response.PageResponseRequest;
import com.xenon.core.service.blog.BlogService;
import com.xenon.data.entity.blog.PostCategory;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @InjectMocks
    private BlogController blogController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BlogPostRequest blogPostRequest;
    private final Long BLOG_ID = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        objectMapper = new ObjectMapper();

        // Initialize blog post request
        blogPostRequest = new BlogPostRequest();
        blogPostRequest.setTitle("Test Blog Title");
        blogPostRequest.setContent("This is the content of the test blog post");
        blogPostRequest.setCategory(PostCategory.MEDICAL_TIPS.name());
        blogPostRequest.setMedia("http://example.com/media.jpg");
    }

    @Test
    void createBlogPostRequest_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog post created successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).createBlogPostRequest(any(BlogPostRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/blog/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogPostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog post created successfully"));

        verify(blogService).createBlogPostRequest(any(BlogPostRequest.class));
    }

    @Test
    void getBlogPosts_ShouldReturnBlogList() throws Exception {
        // Arrange
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 20, 2);

        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog posts retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).getAllBlogs(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog posts retrieved successfully"))
                .andExpect(jsonPath("$.data.pageNumber").value(0))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.totalElements").value(20))
                .andExpect(jsonPath("$.data.totalPages").value(2));

        verify(blogService).getAllBlogs(any(Pageable.class));
    }

    @Test
    void getBlogsByCategory_ShouldReturnCategoryBlogs() throws Exception {
        // Arrange
        String category = "MEDICAL_TIPS";
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 5, 1);

        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog posts retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).getBlogsByCategory(anyString(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/category/{category}", category)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog posts retrieved successfully"));

        verify(blogService).getBlogsByCategory(eq(category), any(Pageable.class));
    }

    @Test
    void getUserBlogs_ShouldReturnUserBlogs() throws Exception {
        // Arrange
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 3, 1);

        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog posts retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).getUserBlogs(any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/user")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog posts retrieved successfully"));

        verify(blogService).getUserBlogs(any(Pageable.class));
    }

    @Test
    void getBlogById_ShouldReturnBlog() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog retrieved successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).getBlogById(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/{id}", BLOG_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog retrieved successfully"));

        verify(blogService).getBlogById(eq(BLOG_ID));
    }

    @Test
    void incrementViewCount_ShouldIncrementAndReturnCount() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "View count incremented", 5);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).incrementViewCount(anyLong());

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/view/{id}", BLOG_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("View count incremented"))
                .andExpect(jsonPath("$.data").value(5));

        verify(blogService).incrementViewCount(eq(BLOG_ID));
    }

    @Test
    void searchBlogs_ShouldReturnMatchingBlogs() throws Exception {
        // Arrange
        String query = "health";
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 3, 1);

        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog posts retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).searchBlogs(anyString(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/search")
                        .param("query", query)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog posts retrieved successfully"));

        verify(blogService).searchBlogs(eq(query), any(Pageable.class));
    }

    @Test
    void getTrendingBlogs_ShouldReturnTrendingBlogs() throws Exception {
        // Arrange
        String trendingBy = "views";
        PageResponseRequest<Object> pageResponse = new PageResponseRequest<>(
                null, 0, 10, 5, 1);

        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog posts retrieved successfully", pageResponse);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).getTrendingBlogs(anyString(), any(Pageable.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/blog/trending/{trendingBy}", trendingBy)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog posts retrieved successfully"));

        verify(blogService).getTrendingBlogs(eq(trendingBy), any(Pageable.class));
    }

    @Test
    void updateBlog_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog updated successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).updateBlog(anyLong(), any(BlogPostRequest.class));

        // Update blog post content
        blogPostRequest.setTitle("Updated Blog Title");
        blogPostRequest.setContent("Updated content for the blog post");

        // Act & Assert
        mockMvc.perform(put("/api/v1/blog/{id}", BLOG_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogPostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog updated successfully"));

        verify(blogService).updateBlog(eq(BLOG_ID), any(BlogPostRequest.class));
    }

    @Test
    void deleteBlog_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        BaseResponse<Object> successResponse = new BaseResponse<>("XS0001", "Blog deleted successfully", null);
        ResponseEntity<BaseResponse<Object>> responseEntity = ResponseEntity.ok(successResponse);

        doReturn(responseEntity).when(blogService).deleteBlog(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/blog/{id}", BLOG_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("XS0001"))
                .andExpect(jsonPath("$.message").value("Blog deleted successfully"));

        verify(blogService).deleteBlog(eq(BLOG_ID));
    }
}