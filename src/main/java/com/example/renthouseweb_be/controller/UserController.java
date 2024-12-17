package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.requests.PasswordRequest;
import com.example.renthouseweb_be.dto.UserDTO;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.response.LogoutResponse;
import com.example.renthouseweb_be.response.VerifyTokenResponse;
import com.example.renthouseweb_be.service.UserService;
import com.example.renthouseweb_be.service.impl.JwtService;
import com.example.renthouseweb_be.utils.ModelMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public UserController(
            JwtService jwtService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            ModelMapperUtil modelMapperUtil) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(new LogoutResponse("MS-LO-01"), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long id, @RequestBody User user) throws CommonException {
        user.setId(id);
        Optional<User> userOptional = this.userService.findById(id);
        Long authenticatedUserId = this.userService.getCurrentUser().getId();
        if (!authenticatedUserId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user1 = userOptional.get();
        user1.setFullName(user.getFullName());
        user1.setAddress(user.getAddress());
        user1.setPhone(user.getPhone());
        user1.setAge(user.getAge());
        user1.setDateOfBirth(user.getDateOfBirth());
        user1.setEmail(user.getEmail());
        user1.setImageUser(user.getImageUser());
        userService.saveUpdate(user1);
        return new ResponseEntity<>(modelMapperUtil.map(user1, UserDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/users/avatar/{id}")
    public ResponseEntity<UserDTO> updateUserAvatar(@PathVariable Long id, @RequestBody User user) throws CommonException {
        Optional<User> userOptional = this.userService.findById(id);
        Long authenticatedUserId = this.userService.getCurrentUser().getId();
        if (!authenticatedUserId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user1 = userOptional.get();
        if (user.getImageUser() != null) {
            user1.setImageUser(user.getImageUser());
        }

        userService.save(user1);
        return new ResponseEntity<>(modelMapperUtil.map(user1, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String name) {
        List<User> userList = (List<User>) userService.searchUserByName(name);
        if (userList == null) {
            List<User> users = (List<User>) userService.findAll();
            return new ResponseEntity<>(modelMapperUtil.mapList(users, UserDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(modelMapperUtil.mapList(userList, UserDTO.class), HttpStatus.OK);
        }
    }

    @PatchMapping("/users/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest) throws CommonException {
        User currentUser = userService.getCurrentUser();
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), currentUser.getPassword())) {
            System.out.println(passwordEncoder.matches(passwordRequest.getOldPassword(), currentUser.getPassword()));
            if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String newPassword = passwordEncoder.encode(passwordRequest.getPassword());
            currentUser.setPassword(newPassword);
            currentUser.setConfirmPassword(newPassword);
            userService.saveUpdate(currentUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponse> activateAccount(@RequestParam String token) {
        boolean activationResult = userService.activateUserAccount(token);
        if (activationResult) {
            return new ResponseEntity<>(new VerifyTokenResponse("MS-VR-01"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new VerifyTokenResponse("ER-VR-01"), HttpStatus.BAD_REQUEST);
        }
    }

}

