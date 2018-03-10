package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.manager.ApplicationTESManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class ApplicationTESDaoDecorator extends
    ContentDaoDecorator<ApplicationTES, MutableApplicationTES, Long, ApplicationTESManager> implements
    ApplicationTESManager {
  @Override
  public List<ApplicationTES> getRivewedCoursesForReadOnlyMode(String pCourseId, String pTeacherId, String pStudentId,
      Integer pSemesterId) {
    return getManager().getRivewedCoursesForReadOnlyMode(pCourseId, pTeacherId, pStudentId, pSemesterId);
  }

  @Override
  public Integer getTotalRecords(String pDeptId) {
    return getManager().getTotalRecords(pDeptId);
  }

  @Override
  public List<ApplicationTES> getRecordsOfAssignedCoursesByHead(Integer pSemesterId, String pDeptId) {
    return getManager().getRecordsOfAssignedCoursesByHead(pSemesterId, pDeptId);
  }

  @Override
  public List<ApplicationTES> getAssignedCoursesByHead(String pFacultyId, Integer pSemesterId) {
    return getManager().getAssignedCoursesByHead(pFacultyId, pSemesterId);
  }

  @Override
  public List<Long> saveAssignedCourses(List<MutableApplicationTES> pMutableList) {
    return getManager().saveAssignedCourses(pMutableList);
  }

  @Override
  public List<MutableApplicationTES> getAssignedCourses(String pFaculryId, Integer pSemesterId) {
    return getManager().getAssignedCourses(pFaculryId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getFacultyMembers(String pDeptId) {
    return getManager().getFacultyMembers(pDeptId);
  }

  @Override
  public List<ApplicationTES> getAlreadyReviewdCourses(String pStudentId, Integer pSemesterId) {
    return getManager().getAlreadyReviewdCourses(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    return getManager().getTeachersInfo(pCourseId, pSemesterId, pSection);
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    return getManager().getAllQuestions(pSemesterId);
  }

  @Override
  public List<MutableApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId,
      String pCourseType, String pSection) {
    return getManager().getReviewEligibleCourses(pStudentId, pSemesterId, pCourseType, pSection);
  }

  @Override
  public String getSemesterName(Integer pCurrentSemester) {
    return getManager().getSemesterName(pCurrentSemester);
  }
}
