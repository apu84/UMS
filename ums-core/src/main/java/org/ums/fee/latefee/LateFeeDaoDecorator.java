package org.ums.fee.latefee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class LateFeeDaoDecorator extends ContentDaoDecorator<LateFee, MutableLateFee, Long, LateFeeManager> implements
    LateFeeManager {
  @Override
  public List<LateFee> getLateFees(Integer pSemesterId) {
    return getManager().getLateFees(pSemesterId);
  }

  @Override
  public List<LateFee> getLateFees(Integer pSemesterId, LateFee.AdmissionType pAdmissionType) {
    return getManager().getLateFees(pSemesterId, pAdmissionType);
  }
}
