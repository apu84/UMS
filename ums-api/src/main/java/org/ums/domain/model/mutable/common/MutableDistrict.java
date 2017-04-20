package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableDistrict extends District, Editable<String>, MutableLastModifier, MutableIdentifier<String> {
  void setDistrictId(final String pDistrictId);

  void setDivisionId(final String pDivisionId);

  void setDistrictName(final String pDistrictName);
}
