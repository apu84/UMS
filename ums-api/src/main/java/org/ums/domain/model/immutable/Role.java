package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableRole;

import java.io.Serializable;
import java.util.Set;

public interface Role extends Serializable, Identifier<Integer>, EditType<MutableRole>, LastModifier {
  String getName();

  Set<String> getPermissions();
}
