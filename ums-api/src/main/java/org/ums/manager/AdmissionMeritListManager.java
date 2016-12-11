package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.AdmissionGroupType;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public interface AdmissionMeritListManager extends
    ContentManager<AdmissionMeritList, MutableAdmissionMeritList, Integer> {
  List<AdmissionMeritList> getMeritList(
      final Semester pSemester,
      final Faculty pFaculty,
      final AdmissionGroupType pAdmissionGroup);
}
