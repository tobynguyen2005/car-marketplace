package com.carmarketplace.service.service;

import com.carmarketplace.service.dto.ServiceBookingRequest;
import com.carmarketplace.service.dto.ServiceBookingResponse;
import com.carmarketplace.service.dto.ServiceResponse;
import com.carmarketplace.service.entity.Service;
import com.carmarketplace.service.entity.ServiceBooking;
import com.carmarketplace.service.repository.ServiceBookingRepository;
import com.carmarketplace.service.repository.ServiceRepository;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final ServiceRepository serviceRepository;
    private final ServiceBookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ServiceResponse> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable)
                .map(s -> new ServiceResponse(s.getId(), s.getTitle(), s.getSlug(), s.getCategory(), s.getDescription(), s.getPrice()));
    }

    @Transactional(readOnly = true)
    public ServiceResponse getServiceBySlug(String slug) {
        Service service = serviceRepository.findAll().stream()
                .filter(s -> s.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return new ServiceResponse(service.getId(), service.getTitle(), service.getSlug(), service.getCategory(), service.getDescription(), service.getPrice());
    }

    @Transactional
    public ServiceBookingResponse createBooking(ServiceBookingRequest request, String currentUserEmail) {
        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        User user = null;
        if (currentUserEmail != null) {
            user = userRepository.findByEmail(currentUserEmail).orElse(null);
        }

        ServiceBooking booking = ServiceBooking.builder()
                .service(service)
                .user(user)
                .customerName(request.customerName())
                .phone(request.phone())
                .email(request.email())
                .bookingDate(request.bookingDate())
                .notes(request.notes())
                .build();

        booking = bookingRepository.save(booking);

        return mapToBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public Page<ServiceBookingResponse> getMyBookings(String email, Pageable pageable) {
        return bookingRepository.findByEmail(email, pageable)
                .map(this::mapToBookingResponse);
    }

    private ServiceBookingResponse mapToBookingResponse(ServiceBooking booking) {
        return new ServiceBookingResponse(
                booking.getId(),
                booking.getService().getTitle(),
                booking.getCustomerName(),
                booking.getPhone(),
                booking.getEmail(),
                booking.getBookingDate(),
                booking.getStatus().name(),
                booking.getNotes(),
                booking.getCreatedAt()
        );
    }
}