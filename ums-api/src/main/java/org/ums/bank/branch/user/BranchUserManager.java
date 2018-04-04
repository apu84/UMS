package org.ums.bank.branch.user;

import org.ums.manager.ContentManager;

import java.util.List;

public interface BranchUserManager extends ContentManager<BranchUser, MutableBranchUser, Long> {
  BranchUser getByUserId(String pUserId);

  List<BranchUser> getUsersByBranch(Long pBranchId);

  BranchUser getUserByEmail(String pEmail);
}
