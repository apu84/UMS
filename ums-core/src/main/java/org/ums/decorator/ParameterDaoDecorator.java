package org.ums.decorator;

import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.mutable.MutableParameter;
import org.ums.manager.ParameterManager;

/**
 * Created by My Pc on 3/13/2016.
 */
public class ParameterDaoDecorator extends
    ContentDaoDecorator<Parameter, MutableParameter, String, ParameterManager> implements
    ParameterManager {
}
