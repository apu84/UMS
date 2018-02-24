package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public interface ChequeRegister extends Serializable, EditType<MutableChequeRegister>, LastModifier, Identifier<Long> {

  Company getCompany();

  String getCompanyId();

  AccountTransaction getAccountTransaction();

  Long getAccountTransactionId();

  String getCheckNo();

  Date getChequeDate();

  String getStatus();

  Date getRealizationDate();

  String getStatFlag();

  String getStatUpFlag();

  Date getModificationDate();

  String getModifiedBy();
}
