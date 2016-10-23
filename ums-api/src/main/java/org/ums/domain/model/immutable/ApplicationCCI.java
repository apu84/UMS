package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationType;

import java.io.Serializable;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface ApplicationCCI extends Serializable, LastModifier,
    EditType<MutableApplicationCCI>, Identifier<Integer> {
  Semester getSemester() throws Exception;

  Integer getSemesterId();

  Student getStudent() throws Exception;

  String getStudentId();

  Course getCourse() throws Exception;

  String getCourseId();

  String getCourseNo();

  String getCourseTitle();

  ApplicationType getApplicationType();

  String getApplicationDate();

  String getExamDate();

  String getExamDateOriginal();

  String getMessage();

  Integer totalStudent();

  Integer getCourseYear();

  Integer getCourseSemester();

  String getRoomNo();

  Integer getRoomId();
}
