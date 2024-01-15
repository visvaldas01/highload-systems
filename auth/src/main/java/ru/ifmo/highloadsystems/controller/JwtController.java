package ru.ifmo.highloadsystems.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {
    JwtTokensUtils jwtTokensUtils;

    @GetMapping("/generate-token")
    public String generateToken(UserDetails userDetails) {
        return jwtTokensUtils.generateToken(userDetails);
    }

    @GetMapping("/get-username")
    public String getUsername(String token) {
        return jwtTokensUtils.getUsername(token);
    }

    @GetMapping("/get-roles")
    public List<String> getRoles(String token) {
        return jwtTokensUtils.getRoles(token);
    }
}
