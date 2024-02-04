package ru.ifmo.highloadsystems.service;

import jakarta.transaction.Transactional;
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
        return userApi.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userApi.loadUserByUsername(username);
    }

    public User getNewUser(RegistrationUserDto registrationUserDto) {
        return userApi.getNewUser(registrationUserDto);
    }

    public List<User> getAll() {
        return userApi.getAll();
    }

    public Optional<User> findById(Long id) {
        return userApi.findById(id);
    }

    public void deleteAll() {
        userApi.deleteAll();
    }
}