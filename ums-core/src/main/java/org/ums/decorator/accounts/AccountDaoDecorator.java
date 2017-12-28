package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.manager.accounts.AccountManager;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class AccountDaoDecorator extends ContentDaoDecorator<Account, MutableAccount, Long, AccountManager> implements
    AccountManager {

}
