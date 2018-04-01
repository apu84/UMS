package org.ums.bank;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBank
    extends
    Bank,
    Editable<String>,
    MutableIdentifier<String>,
    MutableLastModifier {

  void setName(String pName);
}
