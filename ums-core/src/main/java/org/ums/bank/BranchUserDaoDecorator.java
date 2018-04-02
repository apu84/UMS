package org.ums.bank;

import org.ums.decorator.ContentDaoDecorator;

public class BranchUserDaoDecorator extends
    ContentDaoDecorator<BranchUser, MutableBranchUser, String, BranchUserManager> implements BranchUserManager {
}
