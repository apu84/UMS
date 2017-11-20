package org.ums.fee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableFeeCategory extends FeeCategory, Editable<String>, MutableLastModifier,
    MutableIdentifier<String> {
  void setFeeId(String pId);

  void setType(FeeType pType);

  void setFeeTypeId(Integer pTypeId);

  void setName(String pName);

  void setDescription(String pDescription);

  void setDependencies(String pDependencies);
}
