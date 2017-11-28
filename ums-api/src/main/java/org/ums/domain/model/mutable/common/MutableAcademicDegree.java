package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAcademicDegree extends AcademicDegree, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setDegreeType(final int pDegreeType);

  void setDegreeName(final String pDegreeName);

  void setDegreeShortName(final String pDegreeShortName);
}
