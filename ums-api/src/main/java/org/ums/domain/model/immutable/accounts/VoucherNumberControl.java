package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 07-Jan-18.
 */
public interface VoucherNumberControl extends Serializable, LastModifier, EditType<MutableVoucherNumberControl>,
    Identifier<Long> {

  Long getFinAccountYearId();

  FinancialAccountYear getFinAccountYear();

  Long getVoucherId();

  Voucher getVoucher();

  ResetBasis getResetBasis();

  Integer getStartVoucherNo();

  BigDecimal getVoucherLimit();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();

}
