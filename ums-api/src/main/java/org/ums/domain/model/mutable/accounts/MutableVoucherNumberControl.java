package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 07-Jan-18.
 */
public interface MutableVoucherNumberControl extends VoucherNumberControl, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setFinAccountYearId(Long pFinAccountYearId);

  void setVoucherId(Long pVoucherId);

  void setResetBasis(ResetBasis pResetBasis);

  void setStartVoucherNo(Integer pStartVoucherNumber);

  void setVoucherLimit(BigDecimal pVoucherLimit);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
