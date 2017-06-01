package org.ums.usermanagement.role;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;
import java.util.Set;

public interface Role extends Serializable, Identifier<Integer>, EditType<MutableRole>, LastModifier {
  String getName();

  Set<String> getPermissions();
}
