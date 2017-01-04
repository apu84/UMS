package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.manager.AdmissionTotalSeatManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class AdmissionTotalSeatDaoDecorator
    extends
    ContentDaoDecorator<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer, AdmissionTotalSeatManager>
    implements AdmissionTotalSeatManager {

  @Override
  public List<AdmissionTotalSeat> getAdmissionTotalSeat(int pSemesterId, ProgramType pProgramType) {
    return getManager().getAdmissionTotalSeat(pSemesterId, pProgramType);
  }
}
