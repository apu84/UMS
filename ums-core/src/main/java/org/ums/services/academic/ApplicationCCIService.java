package org.ums.services.academic;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.persistent.model.PersistentApplicationCCI;

import java.util.List;

/**
 * Created by My Pc on 7/18/2016.
 */
public interface ApplicationCCIService {
  List<PersistentApplicationCCI> validateForAnomalies(
      List<PersistentApplicationCCI> pApplicationCCIs, List<UGRegistrationResult> results,
      Student pStudent);
}
