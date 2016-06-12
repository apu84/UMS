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

  public PersistentUGTheoryMarks() {
  }

  public PersistentUGTheoryMarks(MutableUGBaseRegistration pMutableUGBaseRegistration) {
    super(pMutableUGBaseRegistration);
  }

  @Override
  public MutableUGTheoryMarks edit() throws Exception {
    return new PersistentUGTheoryMarks(this);
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sTheoryMarksManager.update(this);
    } else {
      sTheoryMarksManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sTheoryMarksManager.delete(this);
  }
}
