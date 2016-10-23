package org.ums.decorator;

import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.ExamGradeManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamGradeDaoDecorator extends
    ContentDaoDecorator<ExamGrade, MutableExamGrade, Object, ExamGradeManager> implements
    ExamGradeManager {

  @Override
  public List<StudentGradeDto> getAllGrades(int semesterId, String courseId, ExamType examType,
      CourseType courseType) throws Exception {
    return getManager().getAllGrades(semesterId, courseId, examType, courseType);
  }

  @Override
  public List<GradeChartDataDto> getChartData(int semesterId, String courseId, ExamType examType,
      CourseType courseType) throws Exception {
    return getManager().getChartData(semesterId, courseId, examType, courseType);
  }

  @Override
  public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId, String courseId,
      ExamType examType) throws Exception {
    return getManager().getMarksSubmissionStatus(semesterId, courseId, examType);
  }

  @Override
  public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(Integer pSemesterId,
      Integer pExamType, Integer pProgramId, Integer year, Integer semester, String teacherId,
      String deptId, String userRole, Integer status) throws Exception {
    return getManager().getMarksSubmissionStatus(pSemesterId, pExamType, pProgramId, year,
        semester, teacherId, deptId, userRole, status);
  }

  @Override
  public boolean saveGradeSheet(MarksSubmissionStatusDto actualStatusDTO,
      List<StudentGradeDto> gradeList) throws Exception {
    return getManager().saveGradeSheet(actualStatusDTO, gradeList);
  }

  @Override
  public boolean insertGradeLog(String userId, String userRole,
      MarksSubmissionStatusDto actualStatusDTO, CourseMarksSubmissionStatus nextStatus,
      List<StudentGradeDto> gradeList) throws Exception {
    return getManager().insertGradeLog(userId, userRole, actualStatusDTO, nextStatus, gradeList);
  }

  @Override
  public int insertMarksSubmissionStatusLog(String userId, String userRole,
      MarksSubmissionStatusDto actualStatusDTO, CourseMarksSubmissionStatus status)
      throws Exception {
    return getManager().insertMarksSubmissionStatusLog(userId, userRole, actualStatusDTO, status);
  }

  @Override
  public boolean updateGradeStatus_Save(MarksSubmissionStatusDto actualStatusDTO,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList) throws Exception {
    return getManager().updateGradeStatus_Save(actualStatusDTO, recheckList, approveList);
  }

  @Override
  public boolean updateGradeStatus_Recheck(MarksSubmissionStatusDto actualStatusDTO,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList) throws Exception {
    return getManager().updateGradeStatus_Recheck(actualStatusDTO, recheckList, approveList);
  }

  @Override
  public boolean updateGradeStatus_Approve(MarksSubmissionStatusDto actualStatusDTO,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList) throws Exception {
    return getManager().updateGradeStatus_Approve(actualStatusDTO, recheckList, approveList);
  }

  @Override
  public int updateCourseMarksSubmissionStatus(MarksSubmissionStatusDto actualStatusDTO,
      CourseMarksSubmissionStatus status) throws Exception {
    return getManager().updateCourseMarksSubmissionStatus(actualStatusDTO, status);
  }

  @Override
  public int updatePartInfo(MarksSubmissionStatusDto requestedStatusDTO) throws Exception {
    return getManager().updatePartInfo(requestedStatusDTO);
  }

  @Override
  public int rejectRecheckRequest(MarksSubmissionStatusDto actualStatusDTO) throws Exception {
    return getManager().rejectRecheckRequest(actualStatusDTO);
  }

  @Override
  public int approveRecheckRequest(MarksSubmissionStatusDto actualStatusDTO) throws Exception {
    return getManager().approveRecheckRequest(actualStatusDTO);
  }

  @Override
  public List<String> getRoleForTeacher(String pTeacherId, int pSemesterId, String pCourseId)
      throws Exception {
    return getManager().getRoleForTeacher(pTeacherId, pSemesterId, pCourseId);
  }

  @Override
  public List<String> getRoleForHead(String pUserId) throws Exception {
    return getManager().getRoleForHead(pUserId);
  }

  @Override
  public List<String> getRoleForCoE(String pUserId) throws Exception {
    return getManager().getRoleForCoE(pUserId);
  }

  @Override
  public List<String> getRoleForVC(String pUserId) throws Exception {
    return getManager().getRoleForVC(pUserId);
  }

  @Override
  public int checkSize(Integer pSemesterId, ExamType pExamType, String pExamDate) {
    return getManager().checkSize(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public int insertGradeSubmissionDeadLineInfo(Integer pSemesterId, ExamType pExamType,
      String pExamDate) {
    return getManager().insertGradeSubmissionDeadLineInfo(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId,
      ExamType pExamType, String pExamDate) {
    return getManager().getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public int updateForGradeSubmissionDeadLine(
      List<MarksSubmissionStatusDto> pMarksSubmissionStatusDtos) throws Exception {
    return getManager().updateForGradeSubmissionDeadLine(pMarksSubmissionStatusDtos);
  }

  public int getTotalStudentCount(MarksSubmissionStatusDto actualStatusDTO) throws Exception {
    return getManager().getTotalStudentCount(actualStatusDTO);
  }

  public List<MarksSubmissionStatusLogDto> getMarksSubmissionLogs(Integer pSemesterId,
      String pCourseId, Integer pExamType) throws Exception {
    return getManager().getMarksSubmissionLogs(pSemesterId, pCourseId, pExamType);
  }

  public List<MarksLogDto> getMarksLogs(Integer pSemesterId, String pCourseId, ExamType pExamType,
      String pStudentId, CourseType pCourseType) throws Exception {
    return getManager().getMarksLogs(pSemesterId, pCourseId, pExamType, pStudentId, pCourseType);
  }

  public Map getUserRoleList(Integer pSemesterId, String pCourseId) throws Exception {
    return getManager().getUserRoleList(pSemesterId, pCourseId);
  }

}
