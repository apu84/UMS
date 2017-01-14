package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import java.io.Serializable;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;

public interface AdmissionStudentCertificate extends Serializable,
    EditType<MutableAdmissionStudentCertificate>, Identifier<Integer>, LastModifier {

  int getCertificateId();

  String getCertificateTitle();

  String getCertificateType();

  String getCertificateCategory();
}
