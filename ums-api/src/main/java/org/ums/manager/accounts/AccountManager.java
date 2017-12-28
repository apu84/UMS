package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface AccountManager extends ContentManager<Account, MutableAccount, Long> {

}
