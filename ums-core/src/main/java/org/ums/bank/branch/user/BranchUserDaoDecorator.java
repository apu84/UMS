package org.ums.bank.branch.user;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class BranchUserDaoDecorator extends ContentDaoDecorator<BranchUser, MutableBranchUser, Long, BranchUserManager>
    implements BranchUserManager {
  @Override
  public BranchUser getByUserId(String pUserId) {
    return getManager().getByUserId(pUserId);
  }

  @Override
  public List<BranchUser> getUsersByBranch(Long pBranchId) {
    return getManager().getUsersByBranch(pBranchId);
  }

  @Override
  public BranchUser getUserByEmail(String pEmail) {
    return getManager().getUserByEmail(pEmail);
  }
}
