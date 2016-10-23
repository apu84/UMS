package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSubGroupCCI;

import java.io.Serializable;

/**
 * Created by My Pc on 7/23/2016.
 */
public interface SubGroupCCI extends Serializable, LastModifier, EditType<MutableSubGroupCCI>,
    Identifier<Integer> {
  Semester getSemester() throws Exception;

  Integer getSemesterId();

  Integer getSubGroupNo();

  Integer getTotalStudent();

  Course getCourse() throws Exception;

  String getCourseId();

  String getCourseNo();

  Integer getCourseYear();

  Integer getCourseSemester();

  String getExamDate();
}
