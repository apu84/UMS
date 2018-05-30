package org.ums.decorator;

import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ExpelledInformationManager;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public class ExpelledInformationDaoDecorator extends
    ContentDaoDecorator<ExpelledInformation, MutableExpelledInformation, Long, ExpelledInformationManager> implements
    ExpelledInformationManager {
}
