package com.rabigol.wowmoneywebapp.service.user;

import com.rabigol.wowmoneywebapp.domain.CurrentUser;
import com.rabigol.wowmoneywebapp.domain.UserCreateForm;
import com.rabigol.wowmoneywebapp.domain.User;
import com.rabigol.wowmoneywebapp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(UserCreateForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")) username = username.substring(0, username.indexOf("@"));
        return username;
    }

    public static Long getUserId() {
        Long currentUserId = 0L;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")) {
            CurrentUser currentUser = (CurrentUser) auth.getPrincipal();
            currentUserId = currentUser.getId();
        }
        return currentUserId;
    }
}
