package com.carmarketplace.service.service;

import com.carmarketplace.service.dto.ServiceBookingResponse;
import com.carmarketplace.service.dto.admin.BookingStatusUpdateRequest;
import com.carmarketplace.service.entity.ServiceBooking;
import com.carmarketplace.service.repository.ServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceBookingService {

    private final ServiceBookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public Page<ServiceBookingResponse> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional
    public ServiceBookingResponse updateBookingStatus(Long bookingId, BookingStatusUpdateRequest request) {
        ServiceBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(request.status());
        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    private ServiceBookingResponse mapToResponse(ServiceBooking booking) {
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