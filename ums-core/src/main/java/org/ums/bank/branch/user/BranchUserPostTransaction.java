package org.ums.bank.branch.user;

import org.ums.services.email.NewIUMSAccountInfoEmailService;
import org.ums.usermanagement.user.UserManager;

public class BranchUserPostTransaction extends BranchUserDaoDecorator {
  private UserManager mUserManager;
  private NewIUMSAccountInfoEmailService mNewIUMSAccountInfoEmailService;

  public UserManager getUserManager() {
    return mUserManager;
  }

  public void setUserManager(UserManager pUserManager) {
    mUserManager = pUserManager;
  }

  public NewIUMSAccountInfoEmailService getNewIUMSAccountInfoEmailService() {
    return mNewIUMSAccountInfoEmailService;
  }

  public void setNewIUMSAccountInfoEmailService(NewIUMSAccountInfoEmailService pNewIUMSAccountInfoEmailService) {
    mNewIUMSAccountInfoEmailService = pNewIUMSAccountInfoEmailService;
  }

  @Override
  public Long create(MutableBranchUser pMutable) {
    // mNewIUMSAccountInfoEmailService.sendEmail();
    return pMutable.getId();
  }
}
