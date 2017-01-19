package org.ums.fee;

import org.ums.decorator.ContentDaoDecorator;

public class FeeCategoryDaoDecorator extends
    ContentDaoDecorator<FeeCategory, MutableFeeCategory, String, FeeCategoryManager> implements
    FeeCategoryManager {
}
