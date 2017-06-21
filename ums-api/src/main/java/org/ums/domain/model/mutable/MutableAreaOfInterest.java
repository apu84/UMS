package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AreaOfInterest;

public interface MutableAreaOfInterest extends AreaOfInterest, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {
  void seTAreaOfInterest(final String pAreaOfInterest);
}
