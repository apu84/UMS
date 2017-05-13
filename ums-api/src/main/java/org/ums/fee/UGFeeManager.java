package org.ums.fee;

import java.util.List;

import org.ums.manager.ContentManager;

public interface UGFeeManager extends ContentManager<UGFee, MutableUGFee, Long> {
  List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId);

  List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId, List<FeeCategory> pCategories);

  List<UGFee> getLatestFee(Integer pFacultyId, Integer pProgramTypeId);
}
