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
  public List<ApplicationTES> getCourseForQuestionWiseReport(String pDeptId, Integer pYear, Integer pSemester,
      Integer pSemesterId) {
    return getManager().getCourseForQuestionWiseReport(pDeptId, pYear, pSemester, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getTeacherListForQuestionWiseReport(String pCourseId, Integer pSemesterId) {
    return getManager().getTeacherListForQuestionWiseReport(pCourseId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getDeptListByFacultyId(Integer pFacultyId) {
    return getManager().getDeptListByFacultyId(pFacultyId);
  }

  @Override
  public List<ApplicationTES> getSectionList(String pCourseId, Integer pSemesterId, String pTeacherId) {
    return getManager().getSectionList(pCourseId, pSemesterId, pTeacherId);
  }

  @Override
  public List<Long> setQuestions(List<MutableApplicationTES> pMutableList) {
    return getManager().setQuestions(pMutableList);
  }

  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    return getManager().getQuestionSemesterMap(pSemesterId);
  }

  @Override
  public Long addQuestions(MutableApplicationTES pMutableList) {
    return getManager().addQuestions(pMutableList);
  }

  @Override
  public List<ApplicationTES> getParametersForReport(String pTeacherId, Integer pSemesterId) {
    return getManager().getParametersForReport(pTeacherId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getFacultyListForReport(String pDeptId, Integer pSemesterId) {
    return getManager().getFacultyListForReport(pDeptId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getDeadlines(String pParameterId, Integer pSemesterId) {
    return getManager().getDeadlines(pParameterId, pSemesterId);
  }

  @Override
  public List<MutableApplicationTES> getEligibleFacultyMembers(String pDeptId, Integer pSemesterId) {
    return getManager().getEligibleFacultyMembers(pDeptId, pSemesterId);
  }

  @Override
  public String getCourseDepartmentMap(String pCourseId, Integer pSemesterId) {
    return getManager().getCourseDepartmentMap(pCourseId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getAssignedReviewableCoursesList(String pTeacherId, Integer pSemesterId) {
    return getManager().getAssignedReviewableCoursesList(pTeacherId, pSemesterId);
  }

  @Override
  public Integer getTotalStudentNumber(String pTeacherId, String pCourseId, Integer pSemesterId) {
    return getManager().getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);
  }

  @Override
  public Double getAverageScore(String pTeacherId, String pCourseId, Long pQuestionId, Integer pSemesterId) {
    return getManager().getAverageScore(pTeacherId, pCourseId, pQuestionId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getDetailedResult(String pTeacherId, String pCourseId, Integer pSemesterId) {
    return getManager().getDetailedResult(pTeacherId, pCourseId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getReviewedCoursesForReadOnlyMode(String pCourseId, String pTeacherId, String pStudentId,
      Integer pSemesterId) {
    return getManager().getReviewedCoursesForReadOnlyMode(pCourseId, pTeacherId, pStudentId, pSemesterId);
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
  public List<MutableApplicationTES> getFacultyMembers(String pDeptId) {
    return getManager().getFacultyMembers(pDeptId);
  }

  @Override
  public List<ApplicationTES> getAlreadyReviewedCourses(String pStudentId, Integer pSemesterId) {
    return getManager().getAlreadyReviewedCourses(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    return getManager().getTeachersInfo(pCourseId, pSemesterId, pSection);
  }

  @Override
  public List<MutableApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId,
      String pCourseType, String pSection) {
    return getManager().getReviewEligibleCourses(pStudentId, pSemesterId, pCourseType, pSection);
  }

}
