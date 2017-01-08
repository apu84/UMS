package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public interface AdmissionTotalSeatManager extends
    ContentManager<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer> {

  List<AdmissionTotalSeat> getAdmissionTotalSeat(final int pSemesterId,
      final ProgramType pProgramType, QuotaType pQuotaType);

}
