package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.Booking;
import com.example.renthouseweb_be.model.Comment;
import com.example.renthouseweb_be.model.House;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.repository.CommentRepository;
import com.example.renthouseweb_be.repository.HouseRepository;
import com.example.renthouseweb_be.repository.UserRepository;
import com.example.renthouseweb_be.requests.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    BookingService bookingService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }
    public Comment save (CommentRequest request) throws CommonException {
        List<Booking> bookings = bookingService.findByUserIdAndHouseIdAndStatusAndDeleteFlag(request.getUserId(),request.getHouseId());
        if (bookings.isEmpty()) {
            throw new CommonException("Không tìm thấy tài khoản hoặc thông tin đặt phòng không hợp lệ");
        }
        else {
            Comment comment = new Comment();
            User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
            House house = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));
            comment.setUser(user);
            comment.setHouse(house);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setStar(request.getStar());
            comment.setContent(request.getContent());
            return commentRepository.save(comment);
        }
    }
    public List<Comment> findAllByHouseId(Long houseId) {
        return commentRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }

    public void deleteComment (Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            comment.get().setDeleteFlag(true);
            commentRepository.save(comment.get());
        }else {
            throw new RuntimeException("Comment is not found.");
        }
    }
}
