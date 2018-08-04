package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.enums.accounts.definitions.voucher.number.control.VoucherType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.VoucherManager;
import org.ums.manager.accounts.VoucherNumberControlManager;
import org.ums.util.Utils;

import java.math.BigDecimal;

@Service
public class VoucherService {
  @Autowired
  private VoucherManager mVoucherManager;
  @Autowired
  private VoucherNumberControlManager mVoucherNumberControlmanager;
  @Autowired
  private CompanyManager mCompanyManager;

  public boolean checkWhetherTheBalanceExceedsVoucherLimit(VoucherType pVoucherType, BigDecimal pBalance) {
    Voucher voucher = mVoucherManager.get(pVoucherType.getId());
    Company company = Utils.getCompany();
    VoucherNumberControl voucherNumberControl = mVoucherNumberControlmanager.getByVoucher(voucher, company);
    if(voucherNumberControl.getVoucherLimit() != null && voucherNumberControl.getVoucherLimit().compareTo(pBalance) < 0)
      return false;
    else
      return true;
  }

}
