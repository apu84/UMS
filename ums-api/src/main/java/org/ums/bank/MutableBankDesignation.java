package org.ums.bank;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBankDesignation
    extends
    BankDesignation,
    Editable<Long>,
    MutableIdentifier<Long>,
    MutableLastModifier {

  void setName(String pName);
}
