package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public interface ApplicationTesSelectedCourses extends Serializable, LastModifier,
    EditType<MutableApplicationTesSelectedCourses>, Identifier<Long> {
  String getApplicationDate();

  String getCourseId();

  String getTeacherId();

  String getSection();

  String getDeptId();

  Integer getSemester();

  String getInsertionDate();

}
