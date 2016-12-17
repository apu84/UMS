package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionStudentResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */
public class MutableAdmissionStudentResource extends Resource {

  @Autowired
  AdmissionStudentResourceHelper mHelper;

}
