package ru.ifmo.highloadsystems.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.rest.UserApi;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserApi userApi;

    public Optional<User> findByUsername(String username) {
        return userApi.findByUsername(username).getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userApi.loadUserByUsername(username).getBody();
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