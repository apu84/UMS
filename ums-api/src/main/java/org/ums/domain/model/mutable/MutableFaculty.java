package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Faculty;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public interface MutableFaculty extends Faculty, Mutable, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setLongName(final String pLongName);

  void setShortName(final String pShortName);
}
