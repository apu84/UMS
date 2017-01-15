package org.ums.fee;

import org.ums.decorator.ContentDaoDecorator;

public class FeeDaoDecorator extends ContentDaoDecorator<Fee, MutableFee, Integer, FeeManager>
    implements FeeManager {
}
