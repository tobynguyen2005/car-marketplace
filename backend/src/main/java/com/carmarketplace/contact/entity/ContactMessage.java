package com.carmarketplace.contact.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import com.carmarketplace.common.enums.ContactMessageStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContactMessageStatus status = ContactMessageStatus.NEW;
}