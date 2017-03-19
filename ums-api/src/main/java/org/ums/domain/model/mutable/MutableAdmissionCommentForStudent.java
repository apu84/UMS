package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionCommentForStudent;

public interface MutableAdmissionCommentForStudent extends AdmissionCommentForStudent, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setSemesterId(final int pSemesterId);

  void setReceiptId(final String pReceiptId);

  void setComment(final String pComment);

  void setCommentedOn(final String pCommentedOn);

}
