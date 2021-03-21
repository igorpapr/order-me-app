package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.security.AuthenticationRequest;

public interface AuthenticationService {

    String authenticateAndCreateJwt(AuthenticationRequest request);

}
