package org.ums.fee;

import org.ums.manager.ContentManager;

import java.util.List;

public interface FeeCategoryManager extends ContentManager<FeeCategory, MutableFeeCategory, String> {
  List<FeeCategory> getFeeCategories(Integer pFeeTypeId);

  FeeCategory getByFeeId(String pFeeId);
}
