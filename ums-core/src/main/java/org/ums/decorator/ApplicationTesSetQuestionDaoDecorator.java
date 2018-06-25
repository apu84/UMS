package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.manager.ApplicationTesSetQuestionManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class ApplicationTesSetQuestionDaoDecorator
    extends
    ContentDaoDecorator<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions, Long, ApplicationTesSetQuestionManager>
    implements ApplicationTesSetQuestionManager {
  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    return getManager().getQuestionSemesterMap(pSemesterId);
  }
}
