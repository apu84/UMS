package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.manager.common.LmsApplicationManager;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsApplicationDaoDecorator extends
    ContentDaoDecorator<LmsApplication, MutableLmsApplication, Integer, LmsApplicationManager> implements
    LmsApplicationManager {

}
