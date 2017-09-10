package org.ums.employee.additional;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface AdditionalInformation extends Serializable, EditType<MutableAdditionalInformation>,
    Identifier<String>, LastModifier {

  String getRoomNo();

  String getExtNo();

  String getAcademicInitial();
}
