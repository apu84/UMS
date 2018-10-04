package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface MutableOptStudentCourseSelection extends OptStudentCourseSelection, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setId(Long pId);

  void setGroupId(Long pGroupId);

  void setSubGroupId(Long pSubGroupId);

  void setStudentId(String pStudentId);

  void setStudentChoice(Integer pStudentChoice);

  void setProgramId(Integer pProgramId);

  void setSemesterId(Integer pSemesterId);

  void setYear(Integer pYear);

  void setSemester(Integer pSemester);

  void setDepartmentId(Integer pDepartmentId);

}
