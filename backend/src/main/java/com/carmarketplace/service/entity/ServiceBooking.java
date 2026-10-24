package com.carmarketplace.service.entity;

import com.carmarketplace.common.entity.AuditableEntity;
import com.carmarketplace.common.enums.ServiceBookingStatus;
import com.carmarketplace.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBooking extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceBookingStatus status = ServiceBookingStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String notes;
}