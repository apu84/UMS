package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroup extends Serializable, LastModifier, EditType<MutableOptOfferedGroup>, Identifier<Long> {
  Long getId();

  String getGroupName();

  Integer getSemesterId();

  String getDepartmentId();

  Department getDepartment();

  Integer getProgramId();

  String getProgramName();

  Integer getIsMandatory();

  Integer getYear();

  Integer getSemester();

}
