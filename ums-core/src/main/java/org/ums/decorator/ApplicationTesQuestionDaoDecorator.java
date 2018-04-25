package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;
import org.ums.manager.ApplicationTesQuestionManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
public class ApplicationTesQuestionDaoDecorator extends
    ContentDaoDecorator<ApplicationTesQuestions, MutableApplicationTesQuestions, Long, ApplicationTesQuestionManager>
    implements ApplicationTesQuestionManager {

}
