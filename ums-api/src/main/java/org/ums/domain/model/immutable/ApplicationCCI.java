package org.ums.domain.model.immutable;

import javafx.application.Application;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationStatus;
import org.ums.enums.ApplicationType;

import java.io.Serializable;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface ApplicationCCI extends Serializable, LastModifier, EditType<MutableApplicationCCI>, Identifier<Long> {

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();

  Course getCourse();

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

  // Rumi
  ApplicationStatus getApplicationStatus();

  Integer getCCIStatus();

  String getGradeLetter();

  Integer getCarryYear();

  Integer getCarrySemester();

  String getFullName();

  Integer getCurrentEnrolledSemester();

  Integer getTotalcarry();

  Integer getTotalApplied();

  Integer getTotalApproved();

  Integer getTotalRejected();

  String getTransactionID();

  Integer getImprovementLimit();

  String getCarryLastDate();

  Integer getRowNumber();
}
