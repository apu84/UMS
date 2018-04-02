package org.ums.bank;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface BranchUser extends Serializable, EditType<MutableBranchUser>, LastModifier, Identifier<String> {

  Branch getBranch();

  String getBranchId();

  String getName();

  BankDesignation getBankDesignation();

  Long getBankDesignationId();
}
