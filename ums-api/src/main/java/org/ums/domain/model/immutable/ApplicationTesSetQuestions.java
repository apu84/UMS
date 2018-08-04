package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public interface ApplicationTesSetQuestions extends Serializable, LastModifier,
    EditType<MutableApplicationTesSetQuestions>, Identifier<Long> {
  String getApplicationDate();

  Long getQuestionId();

  Integer getSemesterId();

}
