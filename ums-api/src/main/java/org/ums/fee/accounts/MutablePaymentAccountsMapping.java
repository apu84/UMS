package org.ums.fee.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.fee.accounts.PaymentAccountsMapping;
import org.ums.fee.FeeType;
import org.ums.domain.model.immutable.Faculty;

public interface MutablePaymentAccountsMapping extends PaymentAccountsMapping, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setFeeType(FeeType pFeeType);

  void setFeeTypeId(Integer pFeeTypeId);

  void setFaculty(Faculty pFaculty);

  void setFacultyId(Integer pFacultyId);

  void setAccount(String pAccount);

  void setAccountDetails(String pAccountDetails);
}
