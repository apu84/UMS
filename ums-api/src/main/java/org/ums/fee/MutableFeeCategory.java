package org.ums.fee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableFeeCategory extends FeeCategory, Editable<String>, MutableLastModifier,
    MutableIdentifier<String> {
  void setFeeId(String pId);

  void setType(FeeCategory.Type pType);

  void setName(String pName);

  void setDescription(String pDescription);
}
