package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableVoucher extends Voucher, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setName(String pName);

  void setShortName(String pShortName);
}
