package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.QuestionCorrectionInfo;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public interface MutableQuestionCorrectionInfo extends QuestionCorrectionInfo, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(final Long pId);

  void setSemesterId(final Integer pSemesterId);

  void setExamType(final Integer pExamType);

  void setProgramId(final Integer pProgramId);

  void setProgramName(final String pProgramName);

  void setYear(final Integer pYear);

  void setSemester(final Integer pSemester);

  void setCourseId(final String pCourseId);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setIncorrectQuestionNo(final String pIncorrectQuestionNo);

  void setTypeOfMistake(final String pTypeOfMistake);

  void setEmployeeId(final String pEmployeeId);

  void setEmployeeName(final String pEmployeeName);

  void setExamDate(final String pExamDate);
}
