package org.ums.bank;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBank extends Bank, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {
  void setCode(String pCode);

  void setName(String pName);
}
