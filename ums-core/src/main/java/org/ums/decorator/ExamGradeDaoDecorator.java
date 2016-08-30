package org.ums.decorator;

import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.manager.ExamGradeManager;

import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
public class ExamGradeDaoDecorator  extends ContentDaoDecorator<ExamGrade, MutableExamGrade, Object, ExamGradeManager> implements ExamGradeManager {

    @Override
    public List<StudentGradeDto> getAllGrades(int semesterId,String courseId, int examType,CourseType courseType) throws Exception {
        return getManager().getAllGrades(semesterId, courseId, examType,courseType);
    }

    @Override
    public List<GradeChartDataDto> getChartData(int semesterId,String courseId, int examType,CourseType courseType) throws Exception {
        return getManager().getChartData(semesterId, courseId, examType,courseType);
    }
    @Override
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId,String courseId, int examType) throws Exception {
        return getManager().getMarksSubmissionStatus(semesterId,courseId, examType);
    }

    @Override
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int pSemesterId,int pExamType,String teacherId,String deptId,String userRole,int status) throws Exception {
        return getManager().getMarksSubmissionStatus(pSemesterId,pExamType,teacherId,deptId,userRole,status);
    }

    @Override
    public boolean saveGradeSheet(int semesterId,String courseId,int examType,CourseType courseType,List<StudentGradeDto> gradeList) throws Exception {
        return getManager().saveGradeSheet(semesterId, courseId, examType, courseType,gradeList);
    }

    @Override
    public boolean updateGradeStatus_Save(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        return getManager().updateGradeStatus_Save(pSemesterId, pCourseId, pExamType,courseType, recheckList,approveList);
    }
    @Override
    public boolean updateGradeStatus_Recheck(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        return getManager().updateGradeStatus_Recheck(pSemesterId, pCourseId, pExamType,courseType, recheckList,approveList);
    }

    @Override
    public boolean updateGradeStatus_Approve(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        return getManager().updateGradeStatus_Approve(pSemesterId, pCourseId, pExamType,courseType, recheckList,approveList);
    }

    @Override
    public int updateCourseMarksSubmissionStatus(int semesterId,String courseId,int examType,CourseType courseType,CourseMarksSubmissionStatus status) throws Exception {
        return getManager().updateCourseMarksSubmissionStatus(semesterId, courseId, examType,courseType, status);
    }

    @Override
    public int updatePartInfo(int pSemesterId,String pCourseId,int pExamType,int pTotalPart,int partA,int partB) throws Exception {
        return getManager().updatePartInfo(pSemesterId, pCourseId,pExamType, pTotalPart, partA,partB);
    }

    @Override
    public int rejectRecheckRequest(int semesterId,String courseId,int examType,CourseType courseType) throws Exception {
        return getManager().rejectRecheckRequest(semesterId, courseId, examType,courseType);
    }

    @Override
    public int approveRecheckRequest(int semesterId,String courseId,int examType,CourseType courseType) throws Exception {
        return getManager().approveRecheckRequest(semesterId, courseId, examType,courseType);
    }


    @Override
    public List<String> getRoleForTeacher(String pTeacherId,int  pSemesterId,String pCourseId) throws Exception {
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
    public int checkSize(Integer pSemesterId, Integer pExamType, String pExamDate) {
        return getManager().checkSize(pSemesterId,pExamType,pExamDate);
    }

    @Override
    public int insertGradeSubmissionDeadLineInfo(Integer pSemesterId, Integer pExamType, String pExamDate) {
        return getManager().insertGradeSubmissionDeadLineInfo(pSemesterId,pExamType,pExamDate);
    }

    @Override
    public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId, Integer pExamType, String pExamDate) {
        return getManager().getGradeSubmissionDeadLine(pSemesterId,pExamType,pExamDate);
    }

    @Override
    public int updateForGradeSubmissionDeadLine(List<MarksSubmissionStatusDto> pMarksSubmissionStatusDtos) throws Exception{
        return getManager().updateForGradeSubmissionDeadLine(pMarksSubmissionStatusDtos);
    }
}
