package org.ums.fee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableFeeType extends FeeType, Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {
  void setDescription(String pDescription);
}
