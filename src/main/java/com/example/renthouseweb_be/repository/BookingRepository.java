package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
}
