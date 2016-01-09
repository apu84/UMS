package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.regular.ProgramType;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;

public class PersistentProgramType implements MutableProgramType {
  private static ContentManager<ProgramType, MutableProgramType, Integer> sManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sManager = (ContentManager<ProgramType, MutableProgramType, Integer>)applicationContext.getBean("programTypeManager");
  }

  int mId;
  String mName;

  public PersistentProgramType() {

  }

  public PersistentProgramType(final ProgramType pProgramType) {
    mId = pProgramType.getId();
    mName = pProgramType.getName();
  }

  public Integer getId() {
    return mId;
  }

  public void setId(final Integer pId) {
    mId = pId;
  }

  public String getName() {
    return mName;
  }

  public void setName(final String pName) {
    mName = pName;
  }

  public void delete() throws Exception {
    sManager.delete(this);
  }

  public void commit(final boolean pUpdate) throws Exception {
    if (pUpdate) {
      sManager.update(this);
    } else {
      sManager.create(this);
    }
  }

  public MutableProgramType edit() throws Exception {
    return new PersistentProgramType(this);
  }

}
