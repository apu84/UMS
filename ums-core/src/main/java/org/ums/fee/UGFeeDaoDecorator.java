package org.ums.fee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class UGFeeDaoDecorator extends ContentDaoDecorator<UGFee, MutableUGFee, Long, UGFeeManager> implements
    UGFeeManager {
  @Override
  public List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId) {
    return getManager().getFee(pFacultyId, pSemesterId);
  }
}
