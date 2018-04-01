package org.ums.bank;

import org.ums.decorator.ContentDaoDecorator;

public class BranchDaoDecorator
    extends
    ContentDaoDecorator<Branch, MutableBranch, String, BranchManager>
    implements
    BranchManager {
}
