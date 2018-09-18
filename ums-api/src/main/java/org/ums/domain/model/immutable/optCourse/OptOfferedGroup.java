package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.immutable.Department;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroup {
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
