package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.DegreeLevel;

public interface MutableDegreeTitle extends DegreeTitle, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setTitle(final String pTitle);

  void setDegreeLevel(final DegreeLevel pDegreeLevel);

  void setDegreeLevelId(final Integer pDegreeLevelId);
}
