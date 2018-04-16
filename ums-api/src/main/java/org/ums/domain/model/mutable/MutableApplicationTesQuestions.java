package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationTesQuestions;

/**
 * Created by Rumi on 4/16/2018.
 */
public interface MutableApplicationTesQuestions extends ApplicationTesQuestions, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setApplicationDate(final String pApplicationDate);

  void setQuestionId(final Integer pQuestionId);

  void setQuestionDetails(final String pQuestionDetails);

  void setObservationType(final Integer pObservationType);

  void setSemester(final Integer pSemesterId);

}
