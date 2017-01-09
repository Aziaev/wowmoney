package com.rabigol.wowmoneywebapp.service.currentuser;

import com.rabigol.wowmoneywebapp.domain.CurrentUser;

public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
}
