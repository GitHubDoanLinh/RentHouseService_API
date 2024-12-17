package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.dto.JwtDTO;
import com.example.renthouseweb_be.dto.UserDTO;
import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.account.JwtResponse;
import com.example.renthouseweb_be.model.account.Role;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.response.ApiResponse;
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

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")

public class AuthController {
    private  final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperUtil modelMapperUtil;
    private final JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    public AuthController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapperUtil modelMapperUtil, JwtService jwtService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapperUtil = modelMapperUtil;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (!userService.isCorrectConfirmPassword(user)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (user.getRoles() != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(roleService.findByName("ROLE_ADMIN"));
                user.setRoles(roles);
            } else {
                Set<Role> roles1 = new HashSet<>();
                roles1.add(roleService.findByName("ROLE_USER"));
                user.setRoles(roles1);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return new ResponseEntity<>(modelMapperUtil.map(user, UserDTO.class), HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
