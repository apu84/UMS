package org.ums.bank;

import org.ums.manager.ContentManager;

public interface BankManager extends ContentManager<Bank, MutableBank, Long> {
  Bank getByCode(String pCode);
}
