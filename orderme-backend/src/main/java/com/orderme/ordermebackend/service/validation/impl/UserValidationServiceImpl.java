package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.UserDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidationServiceImpl extends AbstractDtoValidationService<UserDto> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, UserDto.class);
    }

    @Override
    public void validateCreate(UserDto dto) {
        if (dto != null) {
            validateNotEmptyRequiredFields(dto);
            if (dto.getEmail() != null) {
                validateEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                validatePassword(dto.getPassword());
            }
            if (dto.getFirstName() != null) {
                validateFirstName(dto.getFirstName());
            }
        } else {
            failWithEmptyDto(UserDto.class);
        }
    }

    @Override
    public void validatePatch(UserDto dto) {
        if (dto != null) {
            if (dto.getEmail() != null) {
                validateEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                validatePassword(dto.getPassword());
            }
            if (dto.getFirstName() != null) {
                validateFirstName(dto.getFirstName());
            }
        } else {
            failWithEmptyDto(UserDto.class);
        }
    }

    private void validateNotEmptyRequiredFields(UserDto dto) {
        if (dto.getEmail() == null) {
            failWithMissingParameter("email");
        }
        if (dto.getFirstName() == null) {
            failWithMissingParameter("firstName");
        }
        if (dto.getPassword() == null) {
            failWithMissingParameter("password");
        }
    }

    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("The given email must comply to the email format");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 5) {
            throw new IllegalArgumentException("The password must be at least 5 characters long");
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName.isBlank()) {
            throw new IllegalArgumentException("The first name must not be blank");
        }
    }
}
