package com.example.renthouseweb_be.cronjob;

import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.BookingStatus;
import com.example.renthouseweb_be.repository.BookingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTask {

    private final BookingRepository bookingRepository;

    public ScheduledTask(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(cron = "0 1 7 * * *", zone = "GMT+7")
    public void checkoutSchedule() {
        Iterable<Booking> bookings = bookingRepository.findAllByStatusAndDeleteFlag(BookingStatus.CHECK_IN, false);
        for (Booking booking : bookings) {
            System.out.println(booking.getEndDate());
            System.out.println(new Date());
            if (booking.getEndDate().before(new Date())) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingRepository.save(booking);
            }
        }
    }

    @PostConstruct
    public void updateStatusOnStartup() {
        System.out.println("Server started at: " + new Date());
        Iterable<Booking> bookings = bookingRepository.findAllByStatusAndDeleteFlag(BookingStatus.CHECK_IN, false);
        for (Booking booking : bookings) {
            if (booking.getEndDate().before(new Date())) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingRepository.save(booking);
                System.out.println("Updated booking status for: " + booking.getId());
            }
        }
    }
}