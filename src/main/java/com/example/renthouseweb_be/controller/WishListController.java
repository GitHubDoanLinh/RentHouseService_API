package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.dto.WishListDTO;
import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.WishList;
import com.example.renthouseweb_be.requests.WishListRequest;
import com.example.renthouseweb_be.response.ApiResponse;
import com.example.renthouseweb_be.response.bookingresponse.CancelBookingResponse;
import com.example.renthouseweb_be.service.WishListService;
import com.example.renthouseweb_be.utils.ModelMapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/wishlist")
public class WishListController {
    private final ModelMapperUtil mapperUtil;
    private final WishListService wishListService;

    public WishListController(ModelMapperUtil mapperUtil, WishListService wishListService) {
        this.mapperUtil = mapperUtil;
        this.wishListService = wishListService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<WishListDTO>> showAllByUser(@PathVariable Long userId) {
        List<WishList> wishLists = wishListService.findAllByUserId(userId);
        if (wishLists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(wishLists, WishListDTO.class), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<?> saveWishList(@RequestBody WishListRequest wishListRequest) {
        try {
            WishList wishList = wishListService.save(wishListRequest);
            return new ResponseEntity<>(wishList,HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long wishListId){
        try {
            wishListService.deleteWishList(wishListId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CancelBookingResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }
}
