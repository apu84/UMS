package org.ums.decorator;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableEmploymentType;
import org.ums.manager.EmploymentTypeManager;

public class EmploymentTypeDaoDecorator extends
    ContentDaoDecorator<EmploymentType, MutableEmploymentType, Integer, EmploymentTypeManager> implements
    EmploymentTypeManager {

}
