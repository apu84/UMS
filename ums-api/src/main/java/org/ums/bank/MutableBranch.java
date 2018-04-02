package org.ums.bank;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBranch extends Branch, Editable<String>, MutableIdentifier<String>, MutableLastModifier {

  void setBank(Bank pBank);

  void setBankId(String pBankId);

  void setName(String pName);

  void setContactNo(String pContactNo);

  void setLocation(String pLocation);
}
