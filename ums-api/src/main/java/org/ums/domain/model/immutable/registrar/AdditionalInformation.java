package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;

import java.io.Serializable;

public interface AdditionalInformation extends Serializable, EditType<MutableAdditionalInformation>,
    Identifier<String>, LastModifier {

  String getRoomNo();

  int getExtNo();

  String getAcademicInitial();
}
