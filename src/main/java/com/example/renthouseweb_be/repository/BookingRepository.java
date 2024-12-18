package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
    Iterable<Booking> findAllByHouseIdAndAndDeleteFlag(Long houseId,boolean deleteFlag);
    Iterable<Booking> findAllByStatusAndDeleteFlag(BookingStatus status, boolean deleteFlag);
}
