package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.enums.ExamType;

import java.util.Date;
import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
public interface MutableExamGrade extends ExamGrade, Mutable, MutableIdentifier<Integer> {

    void  setGradeList(List<StudentGradeDto> pGradeList);
    void setSemesterId(int pSemesterId);
    void getSemesterName(String pSemesterName);
    void setExamTypeId(int pExamTypeId);
    void setExamType(ExamType pExamType);
    void setExamTypeName(String pExamTypeName);
    void setCourseId(String  pCourseId);
    void setCourseTitle(String pCourseTitle);
    void setCourseNo(String pCourseNo);
    void setCourseCreditHour(Integer pCourseCreditHour);
    void setLastSubmissionDate(Date pLastSubmissionDate);
    void setExamDate(String pExamDate);
    void setProgramShortName(String pProgramShortName);
    void setTotalStudents(Integer pTotalStudents);
}
