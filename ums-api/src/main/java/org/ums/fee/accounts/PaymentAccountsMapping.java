package org.ums.fee.accounts;

import java.io.Serializable;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.EditType;
import org.ums.fee.accounts.MutablePaymentAccountsMapping;
import org.ums.domain.model.common.LastModifier;
import org.ums.fee.FeeType;
import org.ums.domain.model.immutable.Faculty;

public interface PaymentAccountsMapping extends Serializable, EditType<MutablePaymentAccountsMapping>, LastModifier,
    Identifier<Long> {

  FeeType getFeeType();

  Integer getFeeTypeId();

  Faculty getFaculty();

  Integer getFacultyId();

  String getAccount();

  String getAccountDetails();
}
