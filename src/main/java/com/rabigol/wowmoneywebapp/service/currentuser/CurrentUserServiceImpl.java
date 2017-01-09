package com.rabigol.wowmoneywebapp.service.currentuser;

import com.rabigol.wowmoneywebapp.domain.CurrentUser;
import com.rabigol.wowmoneywebapp.domain.Role;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }
}
