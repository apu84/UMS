package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;

import java.io.Serializable;

public interface AdmissionCommentForStudent extends Serializable,
    EditType<MutableAdmissionCommentForStudent>, Identifier<Integer>, LastModifier {

  int getRowId();

  int getSemesterId();

  String getReceiptId();

  String getComment();

  String getCommentedOn();

}
