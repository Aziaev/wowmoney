package com.rabigol.wowmoneywebapp.domain.validator;

import com.rabigol.wowmoneywebapp.domain.UserCreateForm;
import com.rabigol.wowmoneywebapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserCreateFormValidator implements org.springframework.validation.Validator {

    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password.no_match", "Password do not match");
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()){
            errors.reject("email.exists", "User with this email already exists");
        }
    }
}
