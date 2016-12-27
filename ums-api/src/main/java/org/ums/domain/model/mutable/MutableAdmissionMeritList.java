package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.QuotaType;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public interface MutableAdmissionMeritList extends AdmissionMeritList, Mutable,
    MutableLastModifier, MutableIdentifier<Integer> {
  void setSemester(final Semester pSemester);

  void setMeritListSerialNo(final int pMeritListSerialNo);

  void setReceiptId(final int pReceiptId);

  void setAdmissionRoll(final int pAdmissionRoll);

  void setCandidateName(final String pCandidateName);

  void setAdmissionGroup(final QuotaType pAdmissionGroup);

  void setFaculty(final Faculty pFaculty);
}
