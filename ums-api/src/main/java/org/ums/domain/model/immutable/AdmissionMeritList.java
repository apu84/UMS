package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.QuotaType;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public interface AdmissionMeritList extends Serializable, LastModifier,
    EditType<MutableAdmissionMeritList>, Identifier<Integer> {
  Semester getSemester();

  int getMeritListSerialNo();

  int getReceiptId();

  int getAdmissionRoll();

  String getCandidateName();

  QuotaType getAdmissionGroup();

  Faculty getFaculty();
}
