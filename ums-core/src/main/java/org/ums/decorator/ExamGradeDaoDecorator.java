package org.ums.decorator;

import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.ExamGradeManager;

import java.util.List;
import java.util.Map;

public class ExamGradeDaoDecorator extends ContentDaoDecorator<ExamGrade, MutableExamGrade, Object, ExamGradeManager>
    implements ExamGradeManager {

  @Override
  public List<StudentGradeDto> getAllGrades(int semesterId, String courseId, ExamType examType, CourseType courseType) {
    return getManager().getAllGrades(semesterId, courseId, examType, courseType);
  }

  @Override
  public List<GradeChartDataDto> getChartData(int semesterId, String courseId, ExamType examType, CourseType courseType) {
    return getManager().getChartData(semesterId, courseId, examType, courseType);
  }

  @Override
  public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId, String courseId, ExamType examType) {
    return getManager().getMarksSubmissionStatus(semesterId, courseId, examType);
  }

  @Override
  public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(Integer pSemesterId, Integer pExamType,
      Integer pProgramId, Integer year, Integer semester, String teacherId, String deptId, String userRole,
      Integer status, String pCourseNo) {
    return getManager().getMarksSubmissionStatus(pSemesterId, pExamType, pProgramId, year, semester, teacherId, deptId,
        userRole, status, pCourseNo);
  }

  @Override
  public boolean saveGradeSheet(MarksSubmissionStatus actualStatus, List<StudentGradeDto> gradeList) {
    return getManager().saveGradeSheet(actualStatus, gradeList);
  }

  @Override
  public boolean insertGradeLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus nextStatus, List<StudentGradeDto> gradeList) {
    return getManager().insertGradeLog(userId, userRole, actualStatus, nextStatus, gradeList);
  }

  @Override
  public int insertMarksSubmissionStatusLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus status) {
    return getManager().insertMarksSubmissionStatusLog(userId, userRole, actualStatus, status);
  }

  @Override
  public boolean updateGradeStatus_Save(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    return getManager().updateGradeStatus_Save(actualStatus, recheckList, approveList);
  }

  @Override
  public boolean updateGradeStatus_Recheck(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    return getManager().updateGradeStatus_Recheck(actualStatus, recheckList, approveList);
  }

  @Override
  public boolean updateGradeStatus_Approve(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    return getManager().updateGradeStatus_Approve(actualStatus, recheckList, approveList);
  }

  @Override
  public int rejectRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus) {
    return getManager().rejectRecheckRequest(pMarksSubmissionStatus);
  }

  @Override
  public int approveRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus) {
    return getManager().approveRecheckRequest(pMarksSubmissionStatus);
  }

  @Override
  public List<String> getRoleForTeacher(String pTeacherId, int pSemesterId, String pCourseId) {
    return getManager().getRoleForTeacher(pTeacherId, pSemesterId, pCourseId);
  }

  @Override
  public List<String> getRoleForHead(String pUserId) {
    return getManager().getRoleForHead(pUserId);
  }

  @Override
  public List<String> getRoleForCoE(String pUserId) {
    return getManager().getRoleForCoE(pUserId);
  }

  @Override
  public List<String> getRoleForVC(String pUserId) {
    return getManager().getRoleForVC(pUserId);
  }

  @Override
  public int checkSize(Integer pSemesterId, ExamType pExamType, String pExamDate) {
    return getManager().checkSize(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public int createGradeSubmissionStatus(Integer pSemesterId, ExamType pExamType, String pExamDate) {
    return getManager().createGradeSubmissionStatus(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId, ExamType pExamType,
      String pExamDate, String pOfferedDeptId, CourseType pCourseType) {
    return getManager().getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate, pOfferedDeptId, pCourseType);
  }

  public int getTotalStudentCount(MarksSubmissionStatus actualStatus) {
    return getManager().getTotalStudentCount(actualStatus);
  }

  public List<MarksSubmissionStatusLogDto> getMarksSubmissionLogs(Integer pSemesterId, String pCourseId,
      Integer pExamType) {
    return getManager().getMarksSubmissionLogs(pSemesterId, pCourseId, pExamType);
  }

  public List<MarksLogDto> getMarksLogs(Integer pSemesterId, String pCourseId, ExamType pExamType, String pStudentId,
      CourseType pCourseType) {
    return getManager().getMarksLogs(pSemesterId, pCourseId, pExamType, pStudentId, pCourseType);
  }

  public Map getUserRoleList(Integer pSemesterId, String pCourseId) {
    return getManager().getUserRoleList(pSemesterId, pCourseId);
  }

  @Override
  public List<MarksSubmissionStatDto> getMarksSubmissionStat(Integer pProgramType, Integer pSemesterId, String pDeptId,
      Integer pExamType, String pStatus) throws Exception {
    return getManager().getMarksSubmissionStat(pProgramType, pSemesterId, pDeptId, pExamType, pStatus);
  }

}
