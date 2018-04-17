package org.ums.bank.designation;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBankDesignation extends BankDesignation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {
  void setCode(String pCode);

  void setName(String pName);
}
