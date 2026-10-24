package com.carmarketplace.accessory.repository;

import com.carmarketplace.accessory.entity.Accessory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    // Khóa dòng dữ liệu (Row-level lock) trong DB khi lấy ra để Checkout
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Accessory a WHERE a.id = :id")
    Optional<Accessory> findByIdForUpdate(@Param("id") Long id);
}