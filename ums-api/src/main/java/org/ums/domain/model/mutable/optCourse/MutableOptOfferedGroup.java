package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface MutableOptOfferedGroup extends OptOfferedGroup, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setId(Long pId);

  void setGroupName(final String pGroupName);

  void setSemesterId(final Integer pSemesterId);

  void setDepartmentId(final String pDepartmentId);

  void setDepartment(final Department pDepartment);

  void setProgramId(final Integer pProgramId);

  void setProgramName(final String pProgramName);

  void setIsMandatory(final Integer pIsMandatory);

  void setYear(final Integer pYear);

  void setSemester(final Integer pSemester);
}
