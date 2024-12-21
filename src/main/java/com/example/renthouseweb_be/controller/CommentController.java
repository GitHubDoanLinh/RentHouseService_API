package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.dto.CommentDTO;
import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.Comment;
import com.example.renthouseweb_be.requests.CommentRequest;
import com.example.renthouseweb_be.response.ApiResponse;
import com.example.renthouseweb_be.response.bookingresponse.CancelBookingResponse;
import com.example.renthouseweb_be.service.CommentService;
import com.example.renthouseweb_be.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("/comment")
public class CommentController {

    private final ModelMapperUtil mapperUtil;
    private final CommentService commentService;

    public CommentController(ModelMapperUtil mapperUtil, CommentService commentService) {
        this.mapperUtil = mapperUtil;
        this.commentService = commentService;
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<CommentDTO>> findAllByHouseId(@PathVariable Long houseId) {
        List<Comment> comments = commentService.findAllByHouseId(houseId);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(comments, CommentDTO.class), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            Comment savedComment = commentService.save(commentRequest);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CancelBookingResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }
}