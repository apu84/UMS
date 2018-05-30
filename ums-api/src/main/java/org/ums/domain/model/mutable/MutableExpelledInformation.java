package org.ums.domain.model.mutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ExpelledInformation;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public interface MutableExpelledInformation extends ExpelledInformation, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setStudentId(final String pStudentId);

  void setCourseId(final String pCourseId);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setSemesterId(final Integer pSemesterId);

  void setExamType(final Integer pExamType);

  void setExpelledReason(final String pExpelledReason);

  void setInsertionDate(final String pInsertionDate);

  void setStatus(final Integer pStatus);

  void setExamDate(final String pExamDate);
}
