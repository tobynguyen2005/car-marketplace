package com.carmarketplace.blog.controller;

import com.carmarketplace.blog.dto.BlogResponse;
import com.carmarketplace.blog.service.BlogService;
import com.carmarketplace.common.response.ApiResponse;
import com.carmarketplace.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BlogResponse>>> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<BlogResponse> blogPage = blogService.getBlogs(PageRequest.of(page, size, sort));

        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(blogPage), "Blogs retrieved successfully"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BlogResponse>> getBlogBySlug(@PathVariable String slug) {
        BlogResponse response = blogService.getBlogBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Blog retrieved successfully"));
    }
}