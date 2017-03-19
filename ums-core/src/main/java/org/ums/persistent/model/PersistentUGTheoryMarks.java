package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableUGBaseRegistration;
import org.ums.domain.model.mutable.MutableUGTheoryMarks;
import org.ums.manager.UGTheoryMarksManager;

public class PersistentUGTheoryMarks extends AbstractUGBaseRegistration implements MutableUGTheoryMarks {
  private static UGTheoryMarksManager sTheoryMarksManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTheoryMarksManager = applicationContext.getBean("theoryMarksManager", UGTheoryMarksManager.class);
  }

  public PersistentUGTheoryMarks() {}

  public PersistentUGTheoryMarks(MutableUGBaseRegistration pMutableUGBaseRegistration) {
    super(pMutableUGBaseRegistration);
  }

  @Override
  public MutableUGTheoryMarks edit() {
    return new PersistentUGTheoryMarks(this);
  }

  @Override
  public Long create() {
    return sTheoryMarksManager.create(this);
  }

  @Override
  public void update() {
    sTheoryMarksManager.update(this);
  }

  @Override
  public void delete() {
    sTheoryMarksManager.delete(this);
  }
}
