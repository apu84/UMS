package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;

import java.io.Serializable;

/**
 * Created by kawsu on 1/12/2017.
 */
public interface AdmissionStudentsCertificateComment extends Serializable,
    EditType<MutableAdmissionStudentsCertificateComment>, Identifier<Integer>, LastModifier {

  int getRowId();

  int getSemesterId();

  String getReceiptId();

  String getComment();

}
