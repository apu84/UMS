package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.OptionalCourseApplicationStat;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;
import org.ums.domain.model.readOnly.OptionalCourseOffer;
import org.ums.manager.ContentManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class PersistentOptionalCourseOffer  implements MutableOptionalCourseOffer {

  private static ContentManager<OptionalCourseOffer, MutableOptionalCourseOffer, Object> sOptionalCourseOfferManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOptionalCourseOfferManager = (ContentManager<OptionalCourseOffer, MutableOptionalCourseOffer, Object>) applicationContext.getBean("optionalCourseOfferManager");
  }


  private List<OptionalCourseApplicationStat> mOptionalCourseList;
  private List<MutableCourse> mApprovedCourseList;
  private List<MutableCourse> mCall4ApplicationCourseList;
  private List<MutableCourse> mAppliedStudentList;

  public PersistentOptionalCourseOffer(final MutableOptionalCourseOffer pOriginal) throws Exception {
    mOptionalCourseList = pOriginal.getOptionalCourseList();
    mApprovedCourseList = pOriginal.getApprovedCourseList();
    mCall4ApplicationCourseList = pOriginal.getCall4ApplicationCourseList();
    mAppliedStudentList = pOriginal.getAppliedStudentList();
  }

  @Override
  public MutableOptionalCourseOffer edit() throws Exception {
    throw new NotImplementedException();
  }

  @Override
  public void commit(boolean update) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  public void delete() throws Exception {
    throw new NotImplementedException();
  }

  public List<OptionalCourseApplicationStat> getOptionalCourseList() {
    return mOptionalCourseList;
  }

  @Override
  public void setOptionalCourseList(List<OptionalCourseApplicationStat> mOptionalCourseList) {
    this.mOptionalCourseList = mOptionalCourseList;
  }
  @Override
  public List<MutableCourse> getApprovedCourseList() {
    return mApprovedCourseList;
  }
  @Override
  public void setApprovedCourseList(List<MutableCourse> mApprovedCourseList) {
    this.mApprovedCourseList = mApprovedCourseList;
  }
  @Override
  public List<MutableCourse> getCall4ApplicationCourseList() {
    return mCall4ApplicationCourseList;
  }
  @Override
  public void setCall4ApplicationCourseList(List<MutableCourse> mCall4ApplicationCourseList) {
    this.mCall4ApplicationCourseList = mCall4ApplicationCourseList;
  }
  @Override
  public List<MutableCourse> getAppliedStudentList() {
    return mAppliedStudentList;
  }
  @Override
  public void setAppliedStudentList(List<MutableCourse> mAppliedStudentList) {
    this.mAppliedStudentList = mAppliedStudentList;
  }
}
