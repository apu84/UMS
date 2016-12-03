package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.manager.UGRegistrationResultManager;

public class PersistentUGRegistrationResult extends AbstractUGBaseRegistration implements
    MutableUGRegistrationResult {
  private static UGRegistrationResultManager sRegistrationResultManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRegistrationResultManager =
        applicationContext.getBean("registrationResultManager", UGRegistrationResultManager.class);
  }

  public PersistentUGRegistrationResult() {}

  public PersistentUGRegistrationResult(
      final MutableUGRegistrationResult pMutableUGRegistrationResult) {
    super(pMutableUGRegistrationResult);
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sRegistrationResultManager.update(this);
    }
    else {
      sRegistrationResultManager.create(this);
    }
  }

  @Override
  public void delete() {
    sRegistrationResultManager.delete(this);
  }

  @Override
  public MutableUGRegistrationResult edit() {
    return new PersistentUGRegistrationResult(this);
  }
}
