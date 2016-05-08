package org.ums.manager;

import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;

import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
public interface ExamGradeManager extends ContentManager<ExamGrade, MutableExamGrade, Object> {
    public List<StudentGradeDto> getAllGradeForTheoryCourse(int semesterId,String courseId,int examType) throws Exception;
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int semesterId,String courseId,int examType) throws Exception;
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int semesterId,int examType) throws Exception;
    public boolean saveGradeSheet(int semesterId,String courseId,int examType,List<StudentGradeDto> gradeList) throws Exception;
}

