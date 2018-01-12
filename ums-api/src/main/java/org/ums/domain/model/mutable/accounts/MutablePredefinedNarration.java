package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import java.util.Date;
import org.ums.domain.model.immutable.accounts.Voucher;

public interface MutablePredefinedNarration
    extends
    PredefinedNarration,
    Editable<Long>,
    MutableIdentifier<Long>,
    MutableLastModifier {

  void setVoucher(Voucher pVoucher);

  void setNarration(String pNarration);

  void setVoucherId(Long pVoucherId);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}