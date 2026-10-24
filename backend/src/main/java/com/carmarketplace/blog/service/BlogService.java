package com.carmarketplace.blog.service;

import com.carmarketplace.blog.dto.BlogResponse;
import com.carmarketplace.blog.entity.Blog;
import com.carmarketplace.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogService {

    private final BlogRepository blogRepository;

    public Page<BlogResponse> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable).map(this::mapToResponse);
    }

    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findAll().stream()
                .filter(b -> b.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return mapToResponse(blog);
    }

    private BlogResponse mapToResponse(Blog blog) {
        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getSlug(),
                blog.getContent(),
                blog.getAuthor() != null ? blog.getAuthor().getFullName() : "Admin",
                blog.getStatus().name(),
                blog.getCreatedAt()
        );
    }
}