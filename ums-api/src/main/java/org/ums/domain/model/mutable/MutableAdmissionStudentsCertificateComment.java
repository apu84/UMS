package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;

/**
 * Created by kawsu on 1/12/2017.
 */
public interface MutableAdmissionStudentsCertificateComment extends
    AdmissionStudentsCertificateComment, Mutable, MutableIdentifier<Integer>, MutableLastModifier {

  void setSemesterId(final int pSemesterId);

  void setReceiptId(final String pReceiptId);

  void setComment(final String pComment);

}
