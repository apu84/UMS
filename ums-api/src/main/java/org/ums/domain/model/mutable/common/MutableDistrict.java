package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableDistrict extends District, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

  void setDivisionId(final Integer pDivisionId);

  void setDistrictName(final String pDistrictName);
}
