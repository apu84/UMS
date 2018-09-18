package org.ums.ems.profilemanagement.additional;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAdditionalInformation extends AdditionalInformation, Editable<String>,
    MutableIdentifier<String>, MutableLastModifier {

  void setRoomNo(final String pRoomNo);

  void setExtNo(final String pExtNo);

  void setAcademicInitial(final String pAcademicInitial);
}
