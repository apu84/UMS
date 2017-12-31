package org.ums.usermanagement.userView;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.List;
import java.util.Optional;

public class UserViewDaoDecorator extends ContentDaoDecorator<UserView, MutableUserView, String, UserViewManager>
    implements UserViewManager {
}
