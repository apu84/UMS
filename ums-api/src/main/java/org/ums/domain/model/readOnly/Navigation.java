package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableNavigation;

import java.io.Serializable;

public interface Navigation extends Serializable, Identifier<Integer>, LastModifier, EditType<MutableNavigation> {
  String getTitle();

  String getPermission();

  Navigation getParent() throws Exception;

  Integer getParentId();

  Integer getViewOrder();

  String getLocation();

  String getIconImgClass();

  String getIconColorClass();

  boolean isActive();
}
