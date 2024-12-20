package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.dto.BookingDTO;
import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.BookingStatus;
import com.example.renthouseweb_be.requests.BookingRequest;
import com.example.renthouseweb_be.response.ApiResponse;
import com.example.renthouseweb_be.response.DeleteHouseResponse;
import com.example.renthouseweb_be.service.BookingService;
import com.example.renthouseweb_be.utils.ModelMapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final ModelMapperUtil mapperUtil;

    public BookingController(BookingService bookingService, ModelMapperUtil mapperUtil) {
        this.bookingService = bookingService;
        this.mapperUtil = mapperUtil;
    }

    @PostMapping
    public ResponseEntity<?> booking(@RequestBody BookingRequest request) {
        try {
            return new ResponseEntity<>(bookingService.save(request), HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BookingDTO>> showAllByUserId(@PathVariable Long userId, boolean deleteFlag) {
        List<Booking> bookings = (List<Booking>) bookingService.findAllByUserId(userId,false);
        if (bookings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<BookingDTO>> showAllByHouseId(@PathVariable Long houseId) {
        List<Booking> bookings = (List<Booking>) bookingService.findAllByHouseId(houseId,false);
        if (bookings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<DeleteHouseResponse> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(new DeleteHouseResponse("MS-B2-01"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DeleteHouseResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/total-price")
    public ResponseEntity<Double> getTotalPriceByMonthAndStatusAndUserId(
            @RequestParam("month") int month,
            @RequestParam("status") BookingStatus status,
            @PathVariable("userId") Long userId) {
        try {
            Double totalPrice = bookingService.getTotalPriceByMonthAndStatus(month, status, userId);
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/monthly")
    public ResponseEntity<List<BookingDTO>> getBookingsByMonthAndStatusAndUserId(
            @RequestParam("month") int month,
            @RequestParam("status") BookingStatus status,
            @PathVariable("userId") Long userId) {
        if (status == null || month == 0) {
            List<Booking> bookings = (List<Booking>) bookingService.getAllBookingByHostId(userId);
            return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
        }  else {
            try {
                List<Booking> bookings = (List<Booking>) bookingService.getAllBookingByMonthAndHost(month, status, userId);
                return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<BookingDTO>> getAllBookingByHostId(@PathVariable("userId") Long userId) {
        List<Booking> bookings = (List<Booking>) bookingService.getAllBookingByHostId(userId);
        if (bookings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/status/{idBooking}")
    public ResponseEntity<?> setBookingStatus(@PathVariable Long idBooking){
        try {
            bookingService.setCheckIn(idBooking);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
