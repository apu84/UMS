package org.ums.bank.branch.user;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.ums.services.email.NewIUMSAccountInfoEmailService;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.PersistentUser;
import org.ums.usermanagement.user.User;

public class BranchUserPostTransaction extends BranchUserDaoDecorator {
  private static final String ROLE_NAME = "bankuser";
  private NewIUMSAccountInfoEmailService mNewIUMSAccountInfoEmailService;
  private RoleManager mRoleManager;

  public BranchUserPostTransaction(NewIUMSAccountInfoEmailService pNewIUMSAccountInfoEmailService,
      RoleManager pRoleManager) {
    mNewIUMSAccountInfoEmailService = pNewIUMSAccountInfoEmailService;
    mRoleManager = pRoleManager;
  }

  @Override
  @Transactional
  public Long create(MutableBranchUser pMutable) {
    User user = buildUser(pMutable);
    mNewIUMSAccountInfoEmailService.sendEmail(pMutable.getName(), pMutable.getUserId(),
        String.valueOf(user.getTemporaryPassword()), pMutable.getEmail(), "IUMS", "AUST: IUMS Account Credentials");
    return pMutable.getId();
  }

  private User buildUser(BranchUser pBranchUser) {
    MutableUser user = new PersistentUser();
    user.setPrimaryRole(mRoleManager.getByRoleRoleName(ROLE_NAME));
    user.setId(pBranchUser.getUserId());
    user.setEmail(pBranchUser.getEmail());
    user.setTemporaryPassword(RandomStringUtils.random(6, true, true).toCharArray());
    user.setCreatedBy(SecurityUtils.getSubject().getPrincipal().toString());
    user.setCreatedOn(new Date());
    user.setPassword(null);
    user.setActive(true);
    user.create();
    return user;
  }
}
