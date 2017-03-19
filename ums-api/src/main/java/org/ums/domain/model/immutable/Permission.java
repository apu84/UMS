package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutablePermission;

import java.io.Serializable;
import java.util.Set;

public interface Permission extends Serializable, EditType<MutablePermission>, LastModifier, Identifier<Long> {
  Role getRole();

  Integer getRoleId();

  Set<String> getPermissions();
}
