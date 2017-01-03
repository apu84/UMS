package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.manager.AdmissionTotalSeatManager;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class AdmissionTotalSeatDaoDecorator  extends
    ContentDaoDecorator<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer, AdmissionTotalSeatManager>
    implements AdmissionTotalSeatManager{
}
