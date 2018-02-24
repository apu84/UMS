package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.manager.accounts.ChequeRegisterManager;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public class ChequeRegisterDaoDecorator extends
    ContentDaoDecorator<ChequeRegister, MutableChequeRegister, Long, ChequeRegisterManager> implements
    ChequeRegisterManager {
}
