package org.ums.manager;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public interface ApplicationTesSetQuestionManager extends
    ContentManager<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions, Long> {
  List<ApplicationTES> getQuestionSemesterMap(final Integer pSemesterId);
}
