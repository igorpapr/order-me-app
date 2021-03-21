package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.security.RegistrationRequest;
import com.orderme.ordermebackend.model.entity.Role;
import com.orderme.ordermebackend.model.entity.User;
import com.orderme.ordermebackend.repository.UserRepository;
import com.orderme.ordermebackend.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

     private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerWithRole(RegistrationRequest request, Role role) {
        //TODO send email here and create user by another endpoint
        User userToCreate = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .build();
        userRepository.save(userToCreate);
    }
}
