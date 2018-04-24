package org.ums.manager;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;

import java.util.List;

/**
 * Created by Rumi on 2/20/2018.
 */
public interface ApplicationTESManager extends ContentManager<ApplicationTES, MutableApplicationTES, Long> {

  List<ApplicationTES> getAllQuestions(final Integer pSemesterId);

  List<MutableApplicationTES> getMigrationQuestions(final Integer pSemesterId);

  List<ApplicationTES> getAssignedReviewableCoursesList(final String pTeacherId, final Integer pSemesterId);

  List<ApplicationTES> getDeadlines(final String pParameterId, final Integer pSemesterId);

  Integer getObservationType(final Long pQuestionId);

  List<ApplicationTES> getFacultyListForReport(final String pDeptId, final Integer pSemesterId);

  List<ApplicationTES> getParametersForReport(final String pTeacherId, final Integer pSemesterId);

  String getQuestionDetails(final Long pQuestionId);

  List<ApplicationTES> getDeptListByFacultyId(final Integer pFacultyId);

  String getCourseDepartmentMap(final String pCourseId, final Integer pSemesterId);

  List<ApplicationTES> getSectionList(final String pCourseId, final Integer pSemesterId, String pTeacherId);

  List<ApplicationTES> getAllSectionForSelectedCourse(final String pCourseId, String pTeacherId,
      final Integer pSemesterId);

  Integer getTotalRegisteredStudentForCourse(final String pCourseId, final String pSection, final Integer pSemesterId);

  List<MutableApplicationTES> getEligibleFacultyMembers(final String pDeptId, final Integer pSemesterId);

  Integer getTotalStudentNumber(final String pTeacherId, final String pCourseId, final Integer pSemesterId);

  Double getAverageScore(final String pTeacherId, final String pCourseId, final Long pQuestionId,
      final Integer pSemesterId);

  List<ApplicationTES> getDetailedResult(final String pTeacherId, final String pCourseId, final Integer pSemesterId);

  List<ApplicationTES> getReviewedCoursesForReadOnlyMode(final String pCourseId, final String pTeacherId,
      final String pStudentId, final Integer pSemesterId);

  List<MutableApplicationTES> getReviewEligibleCourses(final String pStudentId, final Integer pSemesterId,
      final String pCourseType, final String pSection);

  List<ApplicationTES> getTeachersInfo(final String pCourseId, final Integer pSemesterId, final String pSection);

  List<ApplicationTES> getAlreadyReviewedCourses(final String pStudentId, final Integer pSemesterId);

  List<MutableApplicationTES> getFacultyMembers(final String pDeptId);

  List<MutableApplicationTES> getAssignedCourses(final String pFacultyId, final Integer pSemesterId);

  List<Long> saveAssignedCourses(final List<MutableApplicationTES> pMutableList);

  List<Long> setQuestions(final List<MutableApplicationTES> pMutableList);

  Long addQuestions(final MutableApplicationTES pMutableList);

  List<MutableApplicationTES> getQuestions();

  List<ApplicationTES> getQuestionSemesterMap(final Integer pSemesterId);

  List<ApplicationTES> getAssignedCoursesByHead(final String pFacultyId, final Integer pSemesterId);

  List<ApplicationTES> getRecordsOfAssignedCoursesByHead(final Integer pSemesterId, final String pDeptId);

  Integer getTotalRecords(final String pDeptId);

  List<ApplicationTES> getCourseForQuestionWiseReport(final String pDeptId, final Integer pYear,
      final Integer pSemester, final Integer pSemesterId);

  List<ApplicationTES> getTeacherListForQuestionWiseReport(final String pCourseId, final Integer pSemesterId);

}
