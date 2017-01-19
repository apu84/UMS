package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import java.io.Serializable;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;

public interface AdmissionAllTypesOfCertificate extends Serializable,
    EditType<MutableAdmissionAllTypesOfCertificate>, Identifier<Integer>, LastModifier {

  int getCertificateId();

  String getCertificateTitle();

  String getCertificateType();

}
