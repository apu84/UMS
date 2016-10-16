package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.enums.ExamType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
public interface ExamGrade extends Serializable, EditType<MutableExamGrade>, Identifier<Integer> {
    List<StudentGradeDto> getGradeList();
    int getSemesterId();
    String getSemesterName();
    int getExamTypeId();
    ExamType getExamType();
    String getExamTypeName();
    String getCourseId();
    String getCourseTitle();
    String getCourseNo();
    Integer getCourseCreditHour();
    Date getLastSubmissionDate();
    String getExamDate();
    String getProgramShortName();
    Integer getTotalStudents();
}
