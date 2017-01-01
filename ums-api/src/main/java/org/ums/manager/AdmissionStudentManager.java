package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public interface AdmissionStudentManager extends
    ContentManager<AdmissionStudent, MutableAdmissionStudent, String> {

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, final QuotaType pQuotaType,
      String unit, ProgramType pProgramType);

  int saveTaletalkData(final List<MutableAdmissionStudent> students);

  int getDataSize(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getMeritList(final int pSemesterId, final QuotaType pQuotaType,
      String pUnit, ProgramType pProgramType);

  int saveMeritList(final List<MutableAdmissionStudent> pStudents);
}
