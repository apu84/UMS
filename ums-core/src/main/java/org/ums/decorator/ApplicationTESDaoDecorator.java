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
  public List<ApplicationTES> getAllSectionForSelectedCourse(String pCourseId, String pTeacherId, Integer pSemesterId) {
    return getManager().getAllSectionForSelectedCourse(pCourseId, pTeacherId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getSectionList(String pCourseId, Integer pSemesterId, String pTeacherId) {
    return getManager().getSectionList(pCourseId, pSemesterId, pTeacherId);
  }

  @Override
  public List<MutableApplicationTES> getMigrationQuestions(Integer pSemesterId) {
    return getManager().getMigrationQuestions(pSemesterId);
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
  public List<MutableApplicationTES> getQuestions() {
    return getManager().getQuestions();
  }

  @Override
  public Long addQuestions(MutableApplicationTES pMutableList) {
    return getManager().addQuestions(pMutableList);
  }

  @Override
  public Integer getTotalRegisteredStudentForCourse(String pCourseId, String pSection, Integer pSemesterId) {
    return getManager().getTotalRegisteredStudentForCourse(pCourseId, pSection, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getDeptList() {
    return getManager().getDeptList();
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
  public List<ApplicationTES> getEligibleFacultyMembers(String pDeptId, Integer pSemesterId) {
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
  public List<ApplicationTES> getAllSemesterNameList() {
    return getAllSemesterNameList();
  }

  @Override
  public String getQuestionDetails(Integer pQuestionId) {
    return getManager().getQuestionDetails(pQuestionId);
  }

  @Override
  public Integer getObservationType(Integer pQuestionId) {
    return getManager().getObservationType(pQuestionId);
  }

  @Override
  public Integer getTotalStudentNumber(String pTeacherId, String pCourseId, Integer pSemesterId) {
    return getManager().getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);
  }

  @Override
  public Double getAverageScore(String pTeacherId, String pCourseId, Integer pQuestionId, Integer pSemesterId) {
    return getManager().getAverageScore(pTeacherId, pCourseId, pQuestionId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getDetailedResult(String pTeacherId, String pCourseId, Integer pSemesterId) {
    return getManager().getDetailedResult(pTeacherId, pCourseId, pSemesterId);
  }

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