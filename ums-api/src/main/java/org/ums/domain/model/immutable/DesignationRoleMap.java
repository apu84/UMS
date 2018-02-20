package org.ums.domain.model.immutable;

import java.io.Serializable;

import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignationRoleMap;
import org.ums.usermanagement.role.Role;

public interface DesignationRoleMap extends Serializable, EditType<MutableDesignationRoleMap>, LastModifier,
    Identifier<Integer> {

  Designation getDesignation();

  Integer getDesignationId();

  Role getRole();

  Integer getRoleId();
}
