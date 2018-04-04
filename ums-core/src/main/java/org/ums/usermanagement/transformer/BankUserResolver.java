package org.ums.usermanagement.transformer;

import org.ums.bank.branch.user.BranchUser;
import org.ums.bank.branch.user.BranchUserManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

public class BankUserResolver implements UserPropertyResolver {
  private BranchUserManager mBranchUserManager;

  public BankUserResolver(BranchUserManager pBranchUserManager) {
    mBranchUserManager = pBranchUserManager;
  }

  @Override
  public boolean supports(Role pRole) {
    return pRole.getName().equalsIgnoreCase("bankuser");
  }

  @Override
  public User resolve(User pUser) {
    MutableUser mutableUser = pUser.edit();
    BranchUser branchUser = mBranchUserManager.getByUserId(pUser.getId());
    mutableUser.setName(branchUser.getName());
    mutableUser.setEmail(branchUser.getEmail());
    return mutableUser;
  }
}
