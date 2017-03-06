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
  public List<GradeChartDataDto> getChartData(int semesterId, String courseId, ExamType examType,
      CourseType courseType);

  public List<StudentGradeDto> getAllGrades(int semesterId, String courseId, ExamType examType,
      CourseType courseType);

  public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId, String courseId,
      ExamType examType);

  public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(Integer pSemesterId,
      Integer pExamType, Integer pProgramId, Integer year, Integer semester, String teacherId,
      String deptId, String userRole, Integer status);

  public boolean saveGradeSheet(MarksSubmissionStatus actualStatus, List<StudentGradeDto> gradeList);

  public boolean insertGradeLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus nextStatus, List<StudentGradeDto> gradeList);

  public int insertMarksSubmissionStatusLog(String userId, String userRole,
      MarksSubmissionStatus actualStatus, CourseMarksSubmissionStatus status);

  public boolean updateGradeStatus_Save(MarksSubmissionStatus actualStatus,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList);

  public boolean updateGradeStatus_Recheck(MarksSubmissionStatus actualStatus,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList);

  public boolean updateGradeStatus_Approve(MarksSubmissionStatus actualStatus,
      List<StudentGradeDto> recheckList, List<StudentGradeDto> approveList);

  public int approveRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus);

  public int rejectRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus);

  public List<String> getRoleForTeacher(String pTeacherId, int pSemesterId, String pCourseId);

  public List<String> getRoleForHead(String pUserId);

  public List<String> getRoleForCoE(String pUserId);

  public List<String> getRoleForVC(String pUserId);

  public int checkSize(Integer pSemesterId, ExamType pExamType, String pExamDate);

  public int createGradeSubmissionStatus(Integer pSemesterId, ExamType pExamType, String pExamDate);

  public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId,
      ExamType pExamType, String pExamDate, String pOfferedDeptId, CourseType pCourseType);

  public int updateDeadline(List<MarksSubmissionStatusDto> pMarksSubmissionStatuses);

  public int getTotalStudentCount(MarksSubmissionStatus actualStatus);

  public List<MarksSubmissionStatusLogDto> getMarksSubmissionLogs(Integer pSemesterId,
      String pCourseId, Integer pExamType);

  public List<MarksLogDto> getMarksLogs(Integer pSemesterId, String pCourseId, ExamType pExamType,
      String pStudentId, CourseType pCourseType);

  public Map getUserRoleList(Integer pSemesterId, String pCourseId);

  public List<MarksSubmissionStatDto> getMarksSubmissionStat(Integer getMarksSubmissionStat,
      Integer pSemesterId, String pDeptId, Integer pExamType, String pStatus) throws Exception;

}
