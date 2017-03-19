package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Navigation;

public interface MutableNavigation extends Navigation, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setTitle(final String pTitle);

  void setPermission(final String pPermission);

  void setParent(final Navigation pNavigation);

  void setParentId(final Long pParentId);

  void setViewOrder(final Integer pViewOrder);

  void setLocation(final String pLocation);

  void setIconImgClass(final String pIconImgClass);

  void setIconColorClass(final String pIconColorClass);

  void setActive(final boolean pActive);
}
