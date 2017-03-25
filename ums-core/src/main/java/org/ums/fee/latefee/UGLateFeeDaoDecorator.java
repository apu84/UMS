package org.ums.fee.latefee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class UGLateFeeDaoDecorator extends ContentDaoDecorator<UGLateFee, MutableUGLateFee, Long, UGLateFeeManager>
    implements UGLateFeeManager {
  @Override
  public List<UGLateFee> getLateFees(Integer pSemesterId) {
    return getManager().getLateFees(pSemesterId);
  }
}
