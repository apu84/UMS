package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAdditionalInformation extends AdditionalInformation, Editable<String>,
    MutableIdentifier<String>, MutableLastModifier {

  void setRoomNo(final String pRoomNo);

  void setExtNo(final int pExtNo);

  void setAcademicInitial(final String pAcademicInitial);
}
