package org.ums.academic.resource.fee.semesterfee;

import java.util.List;
import java.util.Optional;

import org.ums.fee.UGFee;
import org.ums.fee.latefee.LateFee;

class UGFees {
  List<UGFee> mUGFees;
  Optional<LateFee> mUGLateFee;

  public UGFees(List<UGFee> pUGFees) {
    mUGFees = pUGFees;
  }

  public List<UGFee> getUGFees() {
    return mUGFees;
  }

  public Optional<LateFee> getUGLateFee() {
    return mUGLateFee;
  }

  public void setUGLateFee(Optional<LateFee> pUGLateFee) {
    mUGLateFee = pUGLateFee;
  }
}
