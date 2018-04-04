package org.ums.bank.branch.user;

import org.ums.bank.designation.BankDesignation;
import org.ums.bank.branch.Branch;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBranchUser extends BranchUser, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {
  void setUserId(String pUserId);

  void setBranch(Branch pBranch);

  void setBranchId(Long pBranchId);

  void setName(String pName);

  void setBankDesignation(BankDesignation pBankDesignation);

  void setBankDesignationId(Long pBankDesignationId);

  void setEmail(String pEmail);
}
