package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.readOnly.Navigation;

public interface MutableNavigation extends Navigation, Mutable, MutableLastModifier, MutableIdentifier<Integer> {
  void setTitle(final String pTitle);

  void setPermission(final String pPermission);

  void setParent(final Navigation pNavigation);

  void setParentId(final Integer pParentId);

  void setViewOrder(final Integer pViewOrder);

  void setLocation(final String pLocation);

  void setIconContent(final String pIconContent);

  void setActive(final boolean pActive);
}
