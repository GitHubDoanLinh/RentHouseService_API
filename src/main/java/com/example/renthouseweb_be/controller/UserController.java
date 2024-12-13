package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.dto.JwtDTO;
import com.example.renthouseweb_be.requests.PasswordRequest;
import com.example.renthouseweb_be.dto.UserDTO;
import com.example.renthouseweb_be.model.account.JwtResponse;
import com.example.renthouseweb_be.model.account.Role;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.response.VerifyTokenResponse;
import com.example.renthouseweb_be.service.RoleService;
import com.example.renthouseweb_be.service.UserService;
import com.example.renthouseweb_be.service.impl.JwtService;
import com.example.renthouseweb_be.utils.ModelMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public UserController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserService userService,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder,
                          ModelMapperUtil modelMapperUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.roleService = roleService;
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

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles() != null) {
            Role role = roleService.findByName("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role role1 = roleService.findByName("ROLE_USER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        user.setIsOwner(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        return new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(modelMapperUtil.map(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()), JwtDTO.class));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
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
        userService.save(user1);
        return new ResponseEntity<>(modelMapperUtil.map(user1, UserDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/users/avatar/{id}")
    public ResponseEntity<UserDTO> updateUserAvatar(@PathVariable Long id, @RequestBody User user) {
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
            return new ResponseEntity<>(modelMapperUtil.mapList(users,UserDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(modelMapperUtil.mapList(userList, UserDTO.class), HttpStatus.OK);
        }
    }
    @PatchMapping("/users/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest) {
        User currentUser = userService.getCurrentUser();
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), currentUser.getPassword())) {
            if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String newPassword = passwordEncoder.encode(passwordRequest.getPassword());
            currentUser.setPassword(newPassword);
            currentUser.setConfirmPassword(newPassword);
            userService.save(currentUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponse> activateAccount(@RequestParam String token) {
        boolean activationResult = userService.activateUserAccount(token);
        if (activationResult) {
            return new ResponseEntity<>(new VerifyTokenResponse(true,"MS-VR-01"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new VerifyTokenResponse(false,"ER-VR-01"),HttpStatus.BAD_REQUEST);
        }
    }

}

