package org.ums.fee;

import org.ums.decorator.ContentDaoDecorator;

public class FeeTypeDaoDecorator extends ContentDaoDecorator<FeeType, MutableFeeType, Integer, FeeTypeManager>
    implements FeeTypeManager {
}
