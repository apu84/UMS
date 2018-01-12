package org.ums.domain.model.immutable.accounts;

import java.io.Serializable;
import java.util.Date;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;

public interface PredefinedNarration
    extends
    Serializable,
    EditType<MutablePredefinedNarration>,
    LastModifier,
    Identifier<Long> {

  Voucher getVoucher();

  String getNarration();

  Long getVoucherId();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}