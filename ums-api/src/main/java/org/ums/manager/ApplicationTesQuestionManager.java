package org.ums.manager;

import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;

import java.util.List;

/**
 * Created by Rumi on 4/16/2018.
 */
public interface ApplicationTesQuestionManager extends
    ContentManager<ApplicationTesQuestions, MutableApplicationTesQuestions, Long> {
  List<ApplicationTesQuestions> getQuestionInfo(final Integer pQuestionId);

}
