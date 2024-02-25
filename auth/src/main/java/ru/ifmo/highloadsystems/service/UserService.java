package ru.ifmo.highloadsystems.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.dto.UserRoleDto;
import ru.ifmo.highloadsystems.rest.UserApi;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserApi userApi;

    public Optional<User> findByUsername(String username) {
        return userApi.findByUsername(username).getBody();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRoleDto dto = userApi.loadUserByUsername(username).getBody();
        return new org.springframework.security.core.userdetails.User(
                dto.username(),
                dto.password(),
                dto.authorities().stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public User getNewUser(RegistrationUserDto registrationUserDto) {
        return userApi.getNewUser(registrationUserDto).getBody();
    }

    public List<User> getAll() {
        return userApi.getAll().getBody();
    }

    public Optional<User> findById(Long id) {
        return userApi.findById(id).getBody();
    }

    public void deleteAll() {
        userApi.deleteAll();
    }
}