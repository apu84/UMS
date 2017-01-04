package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionTotalSeatResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class MutableAdmissionTotalSeatResource extends Resource {

  @Autowired
  AdmissionTotalSeatResourceHelper mHelper;

}
