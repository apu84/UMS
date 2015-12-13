package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.MutableSemester;
import org.ums.manager.SemesterManager;

import java.util.Date;

public class PersistentSemester implements MutableSemester {

  private static SemesterManager sManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("services-context.xml");
    sManager = applicationContext.getBean(SemesterManager.class);
  }

  private String mId;
  private String mName;
  private Date mStartDate;
  private boolean mStatus;

  public PersistentSemester() {

  }

  public PersistentSemester(final PersistentSemester pOriginal) {
    mId = pOriginal.getId();
    mName = pOriginal.getName();
    mStartDate = pOriginal.getStartDate();
    mStatus = pOriginal.getStatus();
  }

  public String getId() {
    return mId;
  }

  public void setId(final String pId) {
    mId = pId;
  }

  public String getName() {
    return mName;
  }

  public void setName(final String pName) {
    mName = pName;
  }

  public Date getStartDate() {
    return mStartDate;
  }

  public void setStartDate(final Date pStartDate) {
    mStartDate = pStartDate;
  }

  public boolean getStatus() {
    return mStatus;
  }

  public void setStatus(final boolean pStatus) {
    mStatus = pStatus;
  }

  public void delete() throws Exception {
    sManager.delete(this);
  }

  public void commit(final boolean update) throws Exception {
    if (update) {
      sManager.update(this);
    } else {
      sManager.create(this);
    }
  }

  public MutableSemester edit() throws Exception {
    return new PersistentSemester(this);
  }
}
