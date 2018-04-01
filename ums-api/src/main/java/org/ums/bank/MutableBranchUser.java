package org.ums.bank;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBranchUser
    extends
    BranchUser,
    Editable<String>,
    MutableIdentifier<String>,
    MutableLastModifier {

  void setBranch(Branch pBranch);

  void setBranchId(String pBranchId);

  void setName(String pName);

  void setBankDesignation(BankDesignation pBankDesignation);

  void setBankDesignationId(Long pBankDesignationId);
}
