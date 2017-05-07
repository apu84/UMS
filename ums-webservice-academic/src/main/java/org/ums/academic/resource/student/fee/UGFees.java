package org.ums.academic.resource.student.fee;

import java.util.List;
import java.util.Optional;

import org.ums.fee.UGFee;
import org.ums.fee.latefee.UGLateFee;

class UGFees {
  List<UGFee> mUGFees;
  Optional<UGLateFee> mUGLateFee;

  public UGFees(List<UGFee> pUGFees) {
    mUGFees = pUGFees;
  }

  public List<UGFee> getUGFees() {
    return mUGFees;
  }

  public Optional<UGLateFee> getUGLateFee() {
    return mUGLateFee;
  }

  public void setUGLateFee(Optional<UGLateFee> pUGLateFee) {
    mUGLateFee = pUGLateFee;
  }
}
