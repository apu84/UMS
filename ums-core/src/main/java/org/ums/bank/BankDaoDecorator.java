package org.ums.bank;

import org.ums.decorator.ContentDaoDecorator;

public class BankDaoDecorator extends ContentDaoDecorator<Bank, MutableBank, Long, BankManager> implements BankManager {
  @Override
  public Bank getByCode(String pCode) {
    return getManager().getByCode(pCode);
  }
}
