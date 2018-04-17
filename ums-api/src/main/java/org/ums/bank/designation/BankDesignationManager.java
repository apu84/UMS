package org.ums.bank.designation;

import org.ums.manager.ContentManager;

public interface BankDesignationManager extends ContentManager<BankDesignation, MutableBankDesignation, Long> {
  BankDesignation getByCode(final String pCode);
}
