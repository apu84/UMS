package org.ums.decorator;

import org.ums.domain.model.immutable.UgAdmissionLog;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;
import org.ums.manager.UgAdmissionLogManager;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public class UgAdmissionLogDaoDecorator extends
    ContentDaoDecorator<UgAdmissionLog, MutableUgAdmissionLog, Integer, UgAdmissionLogManager>
    implements UgAdmissionLogManager {

}
