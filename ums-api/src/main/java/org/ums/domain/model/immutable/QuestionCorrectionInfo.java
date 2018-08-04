package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public interface QuestionCorrectionInfo extends Serializable, LastModifier, EditType<MutableQuestionCorrectionInfo>,
    Identifier<Long> {
  Long getId();

  Integer getSemesterId();

  Integer getExamType();

  Integer getProgramId();

  String getProgramName();

  Integer getYear();

  Integer getSemester();

  String getCourseId();

  String getCourseNo();

  String getCourseTitle();

  String getIncorrectQuestionNo();

  String getTypeOfMistake();

  String getEmployeeId();

  String getEmployeeName();

  String getExamDate();

}
