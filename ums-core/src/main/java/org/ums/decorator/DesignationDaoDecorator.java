package org.ums.decorator;

import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;
import org.ums.manager.DesignationManager;

public class DesignationDaoDecorator extends
    ContentDaoDecorator<Designation, MutableDesignation, Integer, DesignationManager> implements DesignationManager {
}
