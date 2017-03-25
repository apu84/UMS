package org.ums.fee.latefee;

import java.util.List;

import org.ums.manager.ContentManager;

public interface UGLateFeeManager extends ContentManager<UGLateFee, MutableUGLateFee, Long> {
  List<UGLateFee> getLateFees(final Integer pSemesterId);
}
