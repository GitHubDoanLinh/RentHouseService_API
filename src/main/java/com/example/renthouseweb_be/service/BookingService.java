package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.BookingStatus;
import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.repository.BookingRepository;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.repository.UserRepository;
import com.example.renthouseweb_be.requests.BookingRequest;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }
    public Booking save(BookingRequest request) throws CommonException {
        if (request.getStartDate() == null  || request.getEndDate() == null) {
            throw new CommonException("Ngày đặt phòng không được để trống");
        }
        Booking booking = new Booking();
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
        House house = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setPrice(request.getPrice());
        booking.setCreateAt(request.getCreateAt());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setHouse(house);
        booking.setUser(user);
        booking.setStatus(BookingStatus.IN_PROGRESS);
        return bookingRepository.save(booking);
    }
}
