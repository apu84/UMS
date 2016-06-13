package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableUGBaseRegistration;
import org.ums.domain.model.mutable.MutableUGSessionalMarks;
import org.ums.manager.UGSessionalMarksManager;

public class PersistentUGSessionalMarks extends AbstractUGBaseRegistration implements MutableUGSessionalMarks {
  private static UGSessionalMarksManager sSessionalMarksManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSessionalMarksManager = applicationContext.getBean("sessionalMarksManager", UGSessionalMarksManager.class);
  }

  public PersistentUGSessionalMarks() {
  }

  public PersistentUGSessionalMarks(MutableUGBaseRegistration pMutableUGBaseRegistration) {
    super(pMutableUGBaseRegistration);
  }

  @Override
  public MutableUGSessionalMarks edit() throws Exception {
    return new PersistentUGSessionalMarks(this);
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sSessionalMarksManager.update(this);
    } else {
      sSessionalMarksManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSessionalMarksManager.delete(this);
  }
}
