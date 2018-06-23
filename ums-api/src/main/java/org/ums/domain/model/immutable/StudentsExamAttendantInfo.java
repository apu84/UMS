package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public interface StudentsExamAttendantInfo extends Serializable, LastModifier,
    EditType<MutableStudentsExamAttendantInfo>, Identifier<Long> {

  Integer getProgramId();

  Integer getSemesterId();

  Integer getYear();

  Integer getSemester();

  Integer getExamType();

  Integer getPresentStudents();

  Integer getAbsentStudents();

  Integer getRegisteredStudents();

  String getCourseId();

  String getCourseNo();

  String getCourseTitle();

  String getProgramName();

  String getDeptId();

  String getDeptName();

  String getExamDate();

}
