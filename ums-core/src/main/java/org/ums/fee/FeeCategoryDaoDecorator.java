package org.ums.fee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class FeeCategoryDaoDecorator extends
    ContentDaoDecorator<FeeCategory, MutableFeeCategory, String, FeeCategoryManager> implements FeeCategoryManager {
  @Override
  public List<FeeCategory> getFeeCategories(Integer pFeeTypeId) {
    return getManager().getFeeCategories(pFeeTypeId);
  }
}
