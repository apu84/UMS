package org.ums.microservice;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
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
      subject.login(token);
      return true;
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  protected abstract SecurityManager getSecurityManager();

  protected abstract UMSConfiguration getUMSConfiguration();
}
