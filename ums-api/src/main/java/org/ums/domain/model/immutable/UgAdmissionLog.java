package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public interface UgAdmissionLog extends Serializable, EditType<MutableUgAdmissionLog>,
    LastModifier, Identifier<Integer> {
  String getReceiptId();

  int getSemesterId();

  Semester getSemester();

  String getLogText();

  String getActorId();

  Employee getActor();

  String getInsertionDate();
}
