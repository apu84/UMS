package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutablePredefinedNarration extends PredefinedNarration, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setVoucher(Voucher pVoucher);

  void setNarration(String pNarration);

  void setVoucherId(Long pVoucherId);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
