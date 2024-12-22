package com.example.renthouseweb_be.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtDTO {
    private Long id;
    private String accessToken;
    private Collection<? extends GrantedAuthority> roles;
}
