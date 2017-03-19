package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionDeadline;
import org.ums.domain.model.immutable.Semester;

/**
 * Created by Monjur-E-Morshed on 28-Dec-16.
 */
public interface MutableAdmissionDeadline extends AdmissionDeadline, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {
  void setSemester(final Semester pSemester);

  void setSemesterId(final int pSemesterId);

  void setMeritListStartNo(final int pMeritListStartNo);

  void setMeritListEndNo(final int pMeritListEndNo);

  void setStartDate(final String pStartDate);

  void setEndDate(final String pEndDate);
}
