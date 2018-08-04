package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public interface MutableApplicationTesSetQuestions extends ApplicationTesSetQuestions, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setApplicationDate(final String pApplicationDate);

  void setQuestionId(final Long pQuestionId);

  void setSemesterId(final Integer pSemesterId);
}
