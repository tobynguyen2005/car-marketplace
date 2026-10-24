package com.carmarketplace.contact.service;

import com.carmarketplace.common.enums.ContactMessageStatus;
import com.carmarketplace.contact.dto.ContactRequest;
import com.carmarketplace.contact.entity.ContactMessage;
import com.carmarketplace.contact.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository contactRepository;

    @Transactional
    public void submitMessage(ContactRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .subject(request.subject())
                .message(request.message())
                .status(ContactMessageStatus.NEW)
                .build();

        contactRepository.save(message);
    }
}