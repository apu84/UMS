package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationTES;

import java.io.Serializable;

/**
 * Created by Md Golam Muktadir on 2/20/2018.
 */
public interface ApplicationTES extends Serializable, LastModifier, EditType<MutableApplicationTES>, Identifier<Long> {
  String getApplicationDate();

  Integer getQuestionId();

  String getQuestionDetails();

  Integer getObservationType();

  Integer getPoint();

  String getComment();

  String getReviewEligibleCourses();

  String getSemesterName();

  String getCourseTitle();

  String getCourseNo();

  String getTeacherId();

  String getSection();

  String getDeptId();

  String getProgramShortName();

  String getDeptShortName();

  String getFirstName();

  String getLastName();

  String getStudentId();

  Integer getSemester();

  String getDesignation();

  Integer getStatus();

  String getAppliedDate();

}
