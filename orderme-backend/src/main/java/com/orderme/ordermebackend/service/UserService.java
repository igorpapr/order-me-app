package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.security.AuthenticationRequest;
import com.orderme.ordermebackend.model.dto.security.RegistrationRequest;
import com.orderme.ordermebackend.model.entity.Role;
import com.orderme.ordermebackend.model.entity.User;
import com.orderme.ordermebackend.model.entity.security.AuthenticationResponse;
import org.springframework.stereotype.Service;

public interface UserService {

    void registerWithRole(RegistrationRequest request, Role role);


    //disable user?????

}
