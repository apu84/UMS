package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;

import java.io.Serializable;

public interface AdmissionCertificatesOfStudent extends Serializable,
    EditType<MutableAdmissionCertificatesOfStudent>, Identifier<Integer>, LastModifier {

  int getRowId();

  int getSemesterId();

  String getReceiptId();

  int getCertificateId();

  String getCertificateName();

  String getCertificateType();
}
