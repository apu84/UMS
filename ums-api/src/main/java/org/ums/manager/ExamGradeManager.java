package org.ums.manager;

import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;

import java.util.List;
import java.util.Map;

/**
 * Created by ikh on 4/30/2016.
 */
public interface ExamGradeManager extends ContentManager<ExamGrade, MutableExamGrade, Object> {
  List<GradeChartDataDto> getChartData(int semesterId, String courseId, ExamType examType, CourseType courseType);

  List<StudentGradeDto> getAllGrades(int semesterId, String courseId, ExamType examType, CourseType courseType);

  MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId, String courseId, ExamType examType);

  List<MarksSubmissionStatusDto> getMarksSubmissionStatus(Integer pSemesterId, Integer pExamType, Integer pProgramId,
      Integer year, Integer semester, String teacherId, String deptId, String userRole, Integer status, String pCourseNo);

  boolean saveGradeSheet(MarksSubmissionStatus actualStatus, List<StudentGradeDto> gradeList);

  boolean insertGradeLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus nextStatus, List<StudentGradeDto> gradeList);

  int insertMarksSubmissionStatusLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus status);

  boolean updateGradeStatus_Save(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList);

  boolean updateGradeStatus_Recheck(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList);

  boolean updateGradeStatus_Approve(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList);

  int approveRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus);

  int rejectRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus);

  List<String> getRoleForTeacher(String pTeacherId, int pSemesterId, String pCourseId);

  List<String> getRoleForHead(String pUserId);

  List<String> getRoleForCoE(String pUserId);

  List<String> getRoleForVC(String pUserId);

  int checkSize(Integer pSemesterId, ExamType pExamType, String pExamDate);

  int createGradeSubmissionStatus(Integer pSemesterId, ExamType pExamType, String pExamDate);

  List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId, ExamType pExamType, String pExamDate,
      String pOfferedDeptId, CourseType pCourseType);

  int getTotalStudentCount(MarksSubmissionStatus actualStatus);

  List<MarksSubmissionStatusLogDto> getMarksSubmissionLogs(Integer pSemesterId, String pCourseId, Integer pExamType);

  List<MarksLogDto> getMarksLogs(Integer pSemesterId, String pCourseId, ExamType pExamType, String pStudentId,
      CourseType pCourseType);

  Map getUserRoleList(Integer pSemesterId, String pCourseId);

  List<MarksSubmissionStatDto> getMarksSubmissionStat(Integer getMarksSubmissionStat, Integer pSemesterId,
      String pDeptId, Integer pExamType, String pStatus) throws Exception;

}
