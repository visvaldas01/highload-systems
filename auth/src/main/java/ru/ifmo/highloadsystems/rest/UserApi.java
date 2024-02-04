package ru.ifmo.highloadsystems.rest;


import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user",
        configuration = FeignConfig.class)
public interface UserApi {
    @GetMapping(path = "/find-by-username", produces =  MediaType.APPLICATION_JSON)
    Optional<User> findByUsername(@RequestBody String username);

    @GetMapping(path = "/load-user-bu-username", produces =  MediaType.APPLICATION_JSON)
    UserDetails loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException;

    @GetMapping(path = "/get-new-user", produces =  MediaType.APPLICATION_JSON)
    User getNewUser(@RequestBody RegistrationUserDto registrationUserDto);

    @GetMapping(path = "/get-all", produces =  MediaType.APPLICATION_JSON)
    List<User> getAll();

    @GetMapping(path = "/find-by-id", produces =  MediaType.APPLICATION_JSON)
    Optional<User> findById(@RequestBody Long id);

    @GetMapping(path = "/delete-all", produces =  MediaType.APPLICATION_JSON)
    void deleteAll();

    @Component
    class UserApiFallback implements UserApi{

        @Override
        public Optional<User> findByUsername(@RequestBody String username) {
            return null;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;
        }


        @Override
        public User getNewUser(RegistrationUserDto registrationUserDto) {
            return null;
        }

        @Override
        public List<User> getAll() {
            return null;
        }


        @Override
        public Optional<User> findById(Long id) {
            return null;
        }

        @Override
        public void deleteAll() {

        }
    }
}
