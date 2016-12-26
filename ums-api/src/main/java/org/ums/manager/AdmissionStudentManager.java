package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public interface AdmissionStudentManager extends
    ContentManager<AdmissionStudent, MutableAdmissionStudent, String> {

  List<AdmissionStudent> getTaletalkData(final int pSemesterId);

  int saveTaletalkData(final List<MutableAdmissionStudent> students);

  int getDataSize(final int pSemesterId);
}
