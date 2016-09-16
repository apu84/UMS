package org.ums.manager;

import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;

import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
public interface ExamGradeManager extends ContentManager<ExamGrade, MutableExamGrade, Object> {
    public List<GradeChartDataDto> getChartData(int semesterId,String courseId,int examType,CourseType courseType) throws Exception;
    public List<StudentGradeDto> getAllGrades(int semesterId,String courseId,int examType,CourseType courseType) throws Exception;

    public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId,String courseId,int examType) throws Exception;
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int pSemesterId,int pExamType,int pProgramId,String teacherId,String deptId,String userRole,int status) throws Exception;
    public boolean saveGradeSheet(int semesterId,String courseId,int examType,CourseType courseType,List<StudentGradeDto> gradeList) throws Exception;
    public boolean insertGradeLog(String userId,int semesterId,String courseId,int examType,CourseType courseType,CourseMarksSubmissionStatus currentStatus,List<StudentGradeDto> gradeList) throws Exception;
    public int insertMarksSubmissionStatusLog(String pUserId,int pSemesterId,String pCourseId,int pExamType,CourseMarksSubmissionStatus status) throws Exception ;
    public boolean updateGradeStatus_Save(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public boolean updateGradeStatus_Recheck(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public boolean updateGradeStatus_Approve(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public int updateCourseMarksSubmissionStatus(int semesterId,String courseId,int examType,CourseType courseType,CourseMarksSubmissionStatus status) throws Exception;
    public int updatePartInfo(int pSemesterId,String pCourseId,int pExamType,int pTotalPart,int partA,int partB) throws Exception;


    public int approveRecheckRequest(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception ;
    public int rejectRecheckRequest(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception ;

    public List<String> getRoleForTeacher(String pTeacherId,int  pSemesterId,String pCourseId) throws Exception;
    public List<String> getRoleForHead(String pUserId) throws Exception;
    public List<String> getRoleForCoE(String pUserId) throws Exception;
    public List<String> getRoleForVC(String pUserId) throws Exception;


    public int checkSize(Integer pSemesterId,Integer pExamType, String pExamDate);
    public int insertGradeSubmissionDeadLineInfo(Integer pSemesterId,Integer pExamType, String pExamDate);
    public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId, Integer pExamType, String pExamDate);
    public int updateForGradeSubmissionDeadLine(List<MarksSubmissionStatusDto> pMarksSubmissionStatusDtos) throws Exception;
}

