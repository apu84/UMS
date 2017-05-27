package org.ums.fee.latefee;

import java.util.List;

import org.ums.manager.ContentManager;

public interface LateFeeManager extends ContentManager<LateFee, MutableLateFee, Long> {
  List<LateFee> getLateFees(final Integer pSemesterId);
}
