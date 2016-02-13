package org.ums.domain.model.readOnly;


import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutablePermission;

import java.io.Serializable;
import java.util.Set;

public interface Permission extends Serializable, EditType<MutablePermission>, LastModifier, Identifier<Integer> {
  Role getRole() throws Exception;

  Integer getRoleId();

  Set<String> getPermissions();
}
