package com.carmarketplace.blog.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import com.carmarketplace.common.enums.BlogStatus;
import com.carmarketplace.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BlogStatus status = BlogStatus.DRAFT;
}