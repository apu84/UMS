package org.ums.fee;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.fee.FeeCategory;

public interface MutableFeeCategory extends FeeCategory, Mutable, MutableLastModifier,
    MutableIdentifier<String> {
  void setType(FeeCategory.Type pType);

  void setName(String pName);

  void setDescription(String pDescription);
}
