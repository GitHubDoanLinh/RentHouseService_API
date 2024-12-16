package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.exception.CommonException;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.model.account.UserPrinciple;
import com.example.renthouseweb_be.repository.UserRepository;
import com.example.renthouseweb_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) throws CommonException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CommonException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CommonException("Email đã tồn tại");
        }
        userRepository.save(user);
        userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    @Override
    public User getCurrentUser() {
        User user;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        user = this.findByUsername(userName);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NullPointerException();
        }
        return UserPrinciple.build(user.get());
    }

    @Override
    public boolean checkLogin(User user) {
        Iterable<User> users = this.findAll();
        boolean isCorrectUser = false;
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername()) && currentUser.getPassword().equals(user.getPassword()) && currentUser.isEnabled()) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
    }

    @Override
    public boolean isRegister(User user) {
        boolean isRegister = false;
        Iterable<User> users = this.findAll();
        for(User currentUser : users) {
            if(user.getUsername().equals(currentUser.getUsername())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

    @Override
    public boolean isCorrectConfirmPassword(User user) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

    @Override
    public Iterable<User> searchUserByName(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEnabled(false);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        user.setVerificationTokenExpiryDate(calendar.getTime());
        userRepository.save(user);
        return token;
    }

    @Override
    public boolean activateUserAccount(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user != null && !user.isEnabled()) {
            if(user.getVerificationTokenExpiryDate().after(new Date())){
                user.setEnabled(true);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(user);
    }
}
