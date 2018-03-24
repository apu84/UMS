package org.ums.usermanagement.application;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableApplication extends Application, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setName(final String pName);

  void setDescription(final String pDescription);
}
