package org.ums.domain.model.mutable;

import org.ums.domain.model.common.*;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SubGroupCCI;

import java.io.Serializable;

/**
 * Created by My Pc on 7/23/2016.
 */
public interface MutableSubGroupCCI extends SubGroupCCI,Mutable,MutableLastModifier,MutableIdentifier<Integer> {
  void setSemester(Semester pSemester);
  void setSemesterId(Integer pSemesterId);
  void setSubGroupNo(Integer pSubGroupNo);
  void setTotalStudent(Integer pTotalStudent);
  void setCourse(Course pCourse);
  void setCourseId(String pCourseId);
  void setCourseNo(String pCourseNo);
  void setCourseYear(Integer pCourseYear);
  void setCourseSemester(Integer pCourseSemester);
  void setExamDate(String pExamDate);
}
