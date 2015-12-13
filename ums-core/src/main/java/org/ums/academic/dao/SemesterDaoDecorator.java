package org.ums.academic.dao;


import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;
import org.ums.manager.SemesterManager;

import java.util.List;

public class SemesterDaoDecorator implements SemesterManager {
  private SemesterManager mManager;

  public SemesterManager getManager() {
    return mManager;
  }

  public void setManager(SemesterManager pManager) {
    mManager = pManager;
  }

  public List<Semester> getAll() throws Exception {
    return mManager.getAll();
  }

  public Semester get(final String pSemesterId) throws Exception {
    return mManager.get(pSemesterId);
  }

  public void update(final MutableSemester pSemester) throws Exception {
    mManager.update(pSemester);
  }

  public void delete(final MutableSemester pSemester) throws Exception {
    mManager.delete(pSemester);
  }

  public void create(final MutableSemester pSemester) throws Exception {
    mManager.create(pSemester);
  }
}
