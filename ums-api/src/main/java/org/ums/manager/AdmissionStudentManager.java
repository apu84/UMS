package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public interface AdmissionStudentManager extends
    ContentManager<AdmissionStudent, MutableAdmissionStudent, String> {

}
