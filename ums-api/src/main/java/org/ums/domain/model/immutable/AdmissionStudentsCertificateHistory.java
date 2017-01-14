package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;

import java.io.Serializable;

public interface AdmissionStudentsCertificateHistory extends Serializable,
    EditType<MutableAdmissionStudentsCertificateHistory>, Identifier<Integer>, LastModifier {

  int getRowId();

  int getSemesterId();

  String getReceiptId();

  int getCertificateId();
}
