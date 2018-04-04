package org.ums.bank.branch;

import org.ums.bank.Bank;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBranch extends Branch, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {
  void setCode(String pCode);

  void setBank(Bank pBank);

  void setBankId(Long pBankId);

  void setName(String pName);

  void setContactNo(String pContactNo);

  void setLocation(String pLocation);
}
