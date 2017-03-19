package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionDeadline;
import org.ums.domain.model.mutable.MutableAdmissionStudent;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 28-Dec-16.
 */
public interface AdmissionDeadline extends Serializable, EditType<MutableAdmissionDeadline>, Identifier<Integer>,
    LastModifier {

  Semester getSemester();

  int getSemesterId();

  int getMeritListStartNo();

  int getMeritListEndNo();

  String getStartDate();

  String getEndDate();

}
