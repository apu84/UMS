package org.ums.fee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class UGFeeDaoDecorator extends ContentDaoDecorator<UGFee, MutableUGFee, Long, UGFeeManager> implements
    UGFeeManager {
  @Override
  public UGFee getFee(Integer pFacultyId, Integer pSemesterId, FeeCategory pFeeCategory) {
    return getManager().getFee(pFacultyId, pSemesterId, pFeeCategory);
  }

  @Override
  public List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId) {
    return getManager().getFee(pFacultyId, pSemesterId);
  }

  @Override
  public List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId, List<FeeCategory> pCategories) {
    return getManager().getFee(pFacultyId, pSemesterId, pCategories);
  }

  @Override
  public List<UGFee> getLatestFee(Integer pFacultyId, Integer pSemesterId) {
    return getManager().getLatestFee(pFacultyId, pSemesterId);
  }

  @Override
  public List<Integer> getDistinctSemesterIds(Integer pFacultyId) {
    return getManager().getDistinctSemesterIds(pFacultyId);
  }

}
