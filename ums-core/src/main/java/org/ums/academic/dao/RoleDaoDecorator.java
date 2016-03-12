package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.readOnly.Role;
import org.ums.manager.RoleManager;

/**
 * Created by User on 3/12/2016.
 */
public class RoleDaoDecorator extends ContentDaoDecorator<Role, MutableRole, Integer, RoleManager> implements RoleManager {
}
