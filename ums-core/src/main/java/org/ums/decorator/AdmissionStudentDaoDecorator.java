package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.manager.AdmissionStudentManager;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public class AdmissionStudentDaoDecorator extends ContentDaoDecorator<AdmissionStudent, MutableAdmissionStudent, String, AdmissionStudentManager> implements AdmissionStudentManager {

}
