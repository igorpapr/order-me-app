package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.UserDto;
import com.orderme.ordermebackend.service.UserService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathRoutes.PATH_USERS)
public class UserController {

    private final UserService userService;

    private final DtoValidationService<UserDto> userDtoDtoValidationService;

    public UserController(UserService userService,
                          DtoValidationService<UserDto> userDtoDtoValidationService) {
        this.userService = userService;
        this.userDtoDtoValidationService = userDtoDtoValidationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserById(@RequestBody UserDto dto, @PathVariable UUID id) {
        userDtoDtoValidationService.validatePatch(dto);
        return new ResponseEntity<>(userService.patchUserById(dto, id), HttpStatus.OK);
    }

}
