package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;

import java.io.Serializable;

/**
 * Created by Rumi on 4/16/2018.
 */
public interface ApplicationTesQuestions extends Serializable, LastModifier, EditType<MutableApplicationTesQuestions>,
    Identifier<Long> {
  String getApplicationDate();

  Long getQuestionId();

  String getQuestionDetails();

  Integer getObservationType();

  String getInsertionDate();
}
