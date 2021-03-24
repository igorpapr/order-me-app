package com.orderme.ordermebackend.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orderme.ordermebackend.model.dto.UserDto;
import com.orderme.ordermebackend.model.dto.security.RegistrationRequest;
import com.orderme.ordermebackend.model.dtomappers.UserMapper;
import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.model.entity.Role;
import com.orderme.ordermebackend.model.entity.User;
import com.orderme.ordermebackend.repository.UserRepository;
import com.orderme.ordermebackend.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OneToMany;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

     private final UserRepository userRepository;

     private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't find user with id: " + id));
    }

    @Override
    public User patchUserById(UserDto dto, UUID id) {
        Optional<User> target = userRepository.findById(id);
        User userToPatch = target.orElseThrow(() ->
                new EntityNotFoundException("Couldn't find user to update with id: " + id));
        userMapper.updateUserFromDto(dto, userToPatch);
        return userRepository.save(userToPatch);
    }
}
