package com.rabigol.wowmoneywebapp.service.user;

import com.rabigol.wowmoneywebapp.domain.UserCreateForm;
import com.rabigol.wowmoneywebapp.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);
}
