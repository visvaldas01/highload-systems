package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class JwtController {

    private final JwtTokensUtils jwtTokensUtils;

    @GetMapping("/generate-token")
    public String generateToken(UserDetails userDetails) {
        return jwtTokensUtils.generateToken(userDetails);
    }

    @GetMapping("/get-username")
    public String getUsername(String token) {
        return jwtTokensUtils.getUsername(token);
    }

    @GetMapping("/generate-rolesn")
    public List<String> getRoles(String token) {
        return jwtTokensUtils.getRoles(token);
    }
}
