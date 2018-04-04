package org.ums.bank.designation;

import org.ums.bank.designation.BankDesignation;
import org.ums.bank.designation.BankDesignationManager;
import org.ums.bank.designation.MutableBankDesignation;
import org.ums.decorator.ContentDaoDecorator;

public class BankDesignationDaoDecorator extends
    ContentDaoDecorator<BankDesignation, MutableBankDesignation, Long, BankDesignationManager> implements
    BankDesignationManager {
  @Override
  public BankDesignation getByCode(String pCode) {
    return getManager().getByCode(pCode);
  }
}
