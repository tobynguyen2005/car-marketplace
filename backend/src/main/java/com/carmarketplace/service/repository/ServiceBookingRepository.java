package com.carmarketplace.service.repository;

import com.carmarketplace.service.entity.ServiceBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    Page<ServiceBooking> findByEmail(String email, Pageable pageable);
}