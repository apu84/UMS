package org.ums.bank.branch;

import java.io.Serializable;

import org.ums.bank.Bank;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface Branch extends Serializable, EditType<MutableBranch>, LastModifier, Identifier<Long> {
  String getCode();

  Bank getBank();

  Long getBankId();

  String getName();

  String getContactNo();

  String getLocation();
}
