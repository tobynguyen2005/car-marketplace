package com.carmarketplace.user.repository;

import com.carmarketplace.user.entity.OAuth2Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OAuth2AccountRepository extends JpaRepository<OAuth2Account, Long> {
    Optional<OAuth2Account> findByProviderAndProviderUserId(String provider, String providerUserId);
}