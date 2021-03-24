package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.UserDto;
import com.orderme.ordermebackend.model.dto.security.AuthenticationRequest;
import com.orderme.ordermebackend.model.dto.security.RegistrationRequest;
import com.orderme.ordermebackend.model.entity.Role;
import com.orderme.ordermebackend.model.entity.security.AuthenticationResponse;
import com.orderme.ordermebackend.service.AuthenticationService;
import com.orderme.ordermebackend.service.UserService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathRoutes.PATH_AUTH)
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final DtoValidationService<UserDto> userDtoDtoValidationService;

    public AuthenticationController(UserService userService,
                                    AuthenticationService authenticationService,
                                    DtoValidationService<UserDto> userDtoDtoValidationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userDtoDtoValidationService = userDtoDtoValidationService;
    }

    @PostMapping(PathRoutes.CHILD_PATH_AUTH)
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest credentials) {
        AuthenticationResponse response =
                new AuthenticationResponse(authenticationService.authenticateAndCreateJwt(credentials));
        return ResponseEntity.ok(response);
    }

    @PostMapping(PathRoutes.CHILD_PATH_REGISTER)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();
        userDtoDtoValidationService.validateCreate(userDto);
        userService.registerWithRole(request, Role.USER);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(PathRoutes.CHILD_PATH_ADMIN_REGISTER)
    public ResponseEntity<?> registerAdmin(@RequestBody RegistrationRequest request) {
        userService.registerWithRole(request, Role.ADMIN);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
