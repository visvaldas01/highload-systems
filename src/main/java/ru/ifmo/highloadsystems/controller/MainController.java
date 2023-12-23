package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Доступно всем";
    }

    @Validated
    @GetMapping("/secured")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String securedData() {
        return "Доступно только юзерам с токеном";
    }

    @Validated
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminData() {
        return "Админская панель";
    }

    @Validated
    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}
