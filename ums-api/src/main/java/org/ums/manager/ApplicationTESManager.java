package org.ums.manager;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public interface ApplicationTESManager extends ContentManager<ApplicationTES, MutableApplicationTES, Long> {

  List<ApplicationTES> getAllQuestions(final Integer pSemesterId);

  List<ApplicationTES> getAllSemesterNameList();

  List<ApplicationTES> getAssignedReviewableCoursesList(final String pTeacherId, final Integer pSemesterId);

  Integer getObservationType(final Integer pQuestionId);

  String getQuestionDetails(final Integer pQuestionId);

  String getCourseDepartmentMap(final String pCourseId, final Integer pSemesterId);

  Integer getTotalStudentNumber(final String pTeacherId, final String pCourseId, final Integer pSemesterId);

  Double getAverageScore(final String pTeacherId, final String pCourseId, final Integer pQuestionId,
      final Integer pSemesterId);

  List<ApplicationTES> getDetailedResult(final String pTeacherId, final String pCourseId, final Integer pSemesterId);

  List<ApplicationTES> getRivewedCoursesForReadOnlyMode(final String pCourseId, final String pTeacherId,
      final String pStudentId, final Integer pSemesterId);

  List<MutableApplicationTES> getReviewEligibleCourses(final String pStudentId, final Integer pSemesterId,
      final String pCourseType, final String pSection);

  List<ApplicationTES> getTeachersInfo(final String pCourseId, final Integer pSemesterId, final String pSection);

  String getSemesterName(final Integer pCurrentSemester);

  List<ApplicationTES> getAlreadyReviewdCourses(final String pStudentId, final Integer pSemesterId);

  List<ApplicationTES> getFacultyMembers(final String pDeptId);

  List<MutableApplicationTES> getAssignedCourses(final String pFacultyId, final Integer pSemesterId);

  List<Long> saveAssignedCourses(final List<MutableApplicationTES> pMutableList);

  List<ApplicationTES> getAssignedCoursesByHead(final String pFacultyId, final Integer pSemesterId);

  List<ApplicationTES> getRecordsOfAssignedCoursesByHead(final Integer pSemesterId, final String pDeptId);

  Integer getTotalRecords(final String pDeptId);

}
