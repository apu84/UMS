package org.ums.manager;

import org.ums.domain.model.immutable.UgAdmissionLog;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public interface UgAdmissionLogManager extends
    ContentManager<UgAdmissionLog, MutableUgAdmissionLog, Integer> {

}
