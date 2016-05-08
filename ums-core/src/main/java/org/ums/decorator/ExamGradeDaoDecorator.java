package org.ums.decorator;

import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.manager.ExamGradeManager;

import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
public class ExamGradeDaoDecorator  extends ContentDaoDecorator<ExamGrade, MutableExamGrade, Object, ExamGradeManager> implements ExamGradeManager {

    @Override
    public List<StudentGradeDto> getAllGradeForTheoryCourse(int semesterId,String courseId, int examType) throws Exception {
        return getManager().getAllGradeForTheoryCourse(semesterId,courseId, examType);
    }

    @Override
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId,String courseId, int examType) throws Exception {
        return getManager().getMarksSubmissionStatus(semesterId,courseId, examType);
    }

    @Override
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int semesterId,int examType) throws Exception {
        return getManager().getMarksSubmissionStatus(semesterId, examType);
    }

    @Override
    public boolean saveGradeSheet(int semesterId,String courseId,int examType,List<StudentGradeDto> gradeList) throws Exception {
        return getManager().saveGradeSheet(semesterId, courseId, examType, gradeList);
    }

}
