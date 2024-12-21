package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.BookingStatus;
import com.example.renthouseweb_be.response.HistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
    Iterable<Booking> findAllByHouseIdAndDeleteFlag(Long houseId, boolean deleteFlag);
    Iterable<Booking> findAllByStatusAndDeleteFlag(BookingStatus status, boolean deleteFlag);

    @Query("SELECT SUM(b.price) FROM Booking b " +
            "WHERE MONTH(b.createAt) = :month AND b.status = :status AND b.house.user.id = :userId AND b.deleteFlag = false ")
    Double getTotalPriceByMonthAndStatusAndUserId(@Param("month") int month,
                                                  @Param("status") BookingStatus status,
                                                  @Param("userId") Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE MONTH(b.createAt) = :month " +
            "AND b.status = :status " +
            "AND b.house.user.id = :userId " +
            "AND b.deleteFlag = false")
    Iterable<Booking> getBookingsByMonthAndStatusAndUserId(@Param("month") int month,
                                                           @Param("status") BookingStatus status,
                                                           @Param("userId") Long userId);

    @Query("SELECT b FROM Booking b JOIN b.house h WHERE h.user.id = :userId AND b.deleteFlag = false")
    Iterable<Booking> findBookingsByUserIdAndNotDeleted(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN FETCH b.user " +
            "WHERE b.user.id = :userId " +
            "AND b.house.id = :houseId " +
            "AND b.status = :bookingStatus " +
            "AND b.deleteFlag = :deleteFlag")
    List<Booking> findCompletedBookings(
            Long userId, Long houseId, BookingStatus bookingStatus, boolean deleteFlag);

    @Query("SELECT new com.example.renthouseweb_be.response.HistoryResponse(c.name, b.price, h.name, h.location, b.createAt, h.user.fullName, b.startDate, b.endDate, b.numberOfGuests, b.status, CASE WHEN cm.id > 0 THEN TRUE ELSE FALSE END) FROM Booking b" +
            " LEFT JOIN House h ON b.house.id = h.id" +
            " LEFT JOIN Category c ON h.category.id = c.id" +
            " LEFT JOIN Comment cm ON h.id = cm.house.id AND cm.user.id = :userId" +
            " WHERE 1 = 1" +
            " AND b.user.id = :userId")
    List<HistoryResponse> getHistories(@Param("userId") Long userId);
}
