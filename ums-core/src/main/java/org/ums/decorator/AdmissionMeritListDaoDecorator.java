package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.AdmissionGroupType;
import org.ums.manager.AdmissionMeritListManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public class AdmissionMeritListDaoDecorator
    extends
    ContentDaoDecorator<AdmissionMeritList, MutableAdmissionMeritList, Integer, AdmissionMeritListManager>
    implements AdmissionMeritListManager {
  @Override
  public List<AdmissionMeritList> getMeritList(Semester pSemester, Faculty pFaculty,
      AdmissionGroupType pAdmissionGroup) {
    return getManager().getMeritList(pSemester, pFaculty, pAdmissionGroup);
  }
}
