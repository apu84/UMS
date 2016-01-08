package org.ums.domain.model.regular;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.mutable.MutableRole;

import java.io.Serializable;

public interface Role extends Serializable, Identifier<Integer>, EditType<MutableRole> {
  String getName();
}
