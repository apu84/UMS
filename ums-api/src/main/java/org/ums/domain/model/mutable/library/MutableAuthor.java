package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Ifti on 30-Jan-17.
 */

public interface MutableAuthor extends Author, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setFirstName(final String pFirstName);

  void setMiddleName(final String pMiddleName);

  void setLastName(final String pLastName);

  void setShortName(final String pShortName);

  void setGender(final String pGender);

  void setAddress(final String pAddress);

  void setCountryId(final int pCountryId);

  void setCountry(final Country pCountry);

}
