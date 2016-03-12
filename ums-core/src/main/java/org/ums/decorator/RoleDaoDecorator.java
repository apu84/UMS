package org.ums.decorator;

import org.ums.cache.ContentDaoDecorator;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.immutable.Role;
import org.ums.manager.RoleManager;

/**
 * Created by User on 3/12/2016.
 */
public class RoleDaoDecorator extends ContentDaoDecorator<Role, MutableRole, Integer, RoleManager> implements RoleManager {
}
