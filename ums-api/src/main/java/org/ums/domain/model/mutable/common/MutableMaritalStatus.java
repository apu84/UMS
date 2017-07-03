package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableMaritalStatus extends MaritalStatus, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setMaritalStatus(final String pMaritalStatus);

}
