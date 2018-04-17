package org.ums.bank.branch.user;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.usermanagement.user.UserManager;

public class BranchUserDaoDecorator extends ContentDaoDecorator<BranchUser, MutableBranchUser, Long, BranchUserManager>
    implements BranchUserManager {
  protected UserManager mUserManager;

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
