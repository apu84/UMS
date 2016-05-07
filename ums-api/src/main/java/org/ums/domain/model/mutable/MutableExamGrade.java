package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;

import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
public interface MutableExamGrade extends ExamGrade, Mutable {

    void  setGradeList(List<StudentGradeDto> pGradeList);
    void setSemesterId(int pSemesterId);
    void getSemesterName(String pSemesterName);
    void setExamTypeId(int pExamTypeId);
    void setExamTypeName(String pExamTypeName);
    void setCourseId(String  pCourseId);
    void setCourseTitle(String pCourseTitle);

}
