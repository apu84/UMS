package org.ums.manager;

import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;

import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
public interface ExamGradeManager extends ContentManager<ExamGrade, MutableExamGrade, Object> {
    public List<StudentGradeDto> getAllGradeForTheoryCourse(int semesterId,String courseId,int examType) throws Exception;
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId,String courseId,int examType) throws Exception;
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int pSemesterId,int pExamType,String teacherId,String deptId,String userRole) throws Exception;
    public boolean saveGradeSheet(int semesterId,String courseId,int examType,List<StudentGradeDto> gradeList) throws Exception;
    public boolean updateGradeStatus_Save(int pSemesterId,String pCourseId,int pExamType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public boolean updateGradeStatus_Recheck(int pSemesterId,String pCourseId,int pExamType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public boolean updateGradeStatus_Approve(int pSemesterId,String pCourseId,int pExamType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception ;
    public int updateCourseMarksSubmissionStatus(int semesterId,String courseId,int examType,CourseMarksSubmissionStatus status) throws Exception;
    public int updatePartInfo(int pSemesterId,String pCourseId,int pExamType,int pTotalPart,int partA,int partB) throws Exception;

    public List<String> getRoleForTeacher(String pTeacherId,int  pSemesterId,String pCourseId) throws Exception;
    public List<String> getRoleForHead(String pUserId) throws Exception;
    public List<String> getRoleForCoE(String pUserId) throws Exception;

}

