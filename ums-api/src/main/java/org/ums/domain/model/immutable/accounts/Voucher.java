package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableVoucher;

import java.io.Serializable;

public interface Voucher extends Serializable, EditType<MutableVoucher>, LastModifier, Identifier<Long> {

  String getName();

  String getShortName();
}
