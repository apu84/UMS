package org.ums.bank.branch.user;

import java.io.Serializable;

import org.ums.bank.designation.BankDesignation;
import org.ums.bank.branch.Branch;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface BranchUser extends Serializable, EditType<MutableBranchUser>, LastModifier, Identifier<Long> {
  String getUserId();

  Branch getBranch();

  Long getBranchId();

  String getName();

  BankDesignation getBankDesignation();

  Long getBankDesignationId();

  String getEmail();
}
