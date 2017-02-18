package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public interface AdditionalRolePermissions extends Serializable, Identifier<Long>, LastModifier,
    EditType<MutableAdditionalRolePermissions> {
  User getUser();

  String getUserId();

  Role getRole();

  Integer getRoleId();

  Set<String> getPermission();

  Date getValidFrom();

  Date getValidTo();

  boolean isActive();

  User getAssignedBy();

  String getAssignedByUserId();
}
