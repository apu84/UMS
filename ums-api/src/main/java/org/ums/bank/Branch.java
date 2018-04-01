package org.ums.bank;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface Branch
    extends
    Serializable,
    EditType<MutableBranch>,
    LastModifier,
    Identifier<String> {

  Bank getBank();

  String getBankId();

  String getName();

  String getContactNo();
}
