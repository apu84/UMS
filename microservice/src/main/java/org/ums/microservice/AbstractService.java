package org.ums.microservice;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.ums.configuration.UMSConfiguration;

public abstract class AbstractService implements Service {
  protected boolean login() {
    SecurityUtils.setSecurityManager(getSecurityManager());
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token =
        new UsernamePasswordToken(getUMSConfiguration().getBackendUser(), getUMSConfiguration()
            .getBackendUserPassword());

    try {
      ensureUserIsLoggedOut();
      subject.login(token);
      return true;
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  // Logout the user fully before continuing.
  private void ensureUserIsLoggedOut() {
    try {
      // Get the user if one is logged in.
      Subject currentUser = SecurityUtils.getSubject();
      if(currentUser == null)
        return;

      // Log the user out and kill their session if possible.
      currentUser.logout();
      Session session = currentUser.getSession(false);
      if(session == null)
        return;

      session.stop();
    } catch(Exception e) {
      // Ignore all errors, as we're trying to silently
      // log the user out.
    }
  }

  protected abstract SecurityManager getSecurityManager();

  protected abstract UMSConfiguration getUMSConfiguration();
}
