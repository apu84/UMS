package org.ums.domain.model;

import java.io.Serializable;

public interface Role extends Serializable, Identifier<Integer>, EditType<MutableRole> {
  String getName();
}
