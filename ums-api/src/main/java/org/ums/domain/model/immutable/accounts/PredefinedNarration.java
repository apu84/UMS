package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;

import java.io.Serializable;
import java.util.Date;

public interface PredefinedNarration extends Serializable, EditType<MutablePredefinedNarration>, LastModifier,
    Identifier<Long> {

  Voucher getVoucher();

  Company getCompany();

  String getCompanyId();

  String getNarration();

  Long getVoucherId();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}
