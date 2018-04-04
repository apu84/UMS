package org.ums.bank.designation;

import java.io.Serializable;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.LastModifier;

public interface BankDesignation extends Serializable, EditType<MutableBankDesignation>, LastModifier, Identifier<Long> {
  String getCode();

  String getName();
}
