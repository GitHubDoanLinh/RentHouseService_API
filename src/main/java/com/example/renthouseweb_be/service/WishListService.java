package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.model.WishList;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.repository.UserRepository;
import com.example.renthouseweb_be.repository.WishListRepository;
import com.example.renthouseweb_be.requests.WishListRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    public WishListService(WishListRepository wishListRepository,
                           UserRepository userRepository,
                           HouseRepository houseRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }
    public WishList save(WishListRequest wishListRequest) throws CommonException {
        WishList wishList = new WishList();
        User user = userRepository.findById(wishListRequest.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
        House house = houseRepository.findById(wishListRequest.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));
        wishList.setUser(user);
        wishList.setHouse(house);
        wishList.setCreatedAt(LocalDateTime.now());
        return wishListRepository.save(wishList);
    }
    public List<WishList> findAllByUserId(Long userId){
        return wishListRepository.findAllByUserIdAndDeleteFlag(userId,false);
    }
    public void deleteWishList(Long wishListId) {
        Optional<WishList> wishList = wishListRepository.findById(wishListId);
        if (wishList.isPresent()){
            wishList.get().setDeleteFlag(true);
            wishListRepository.save(wishList.get());
        }
    }
}
