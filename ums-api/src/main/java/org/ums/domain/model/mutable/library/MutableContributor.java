package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.Gender;
import org.ums.enums.library.ContributorCategory;

/**
 * Created by Ifti on 16-Feb-17.
 */
public interface MutableContributor extends Contributor, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setFirstName(final String pFirstName);

  void setMiddleName(final String pMiddleName);

  void setLastName(final String pLastName);

  void setShortName(final String pShortName);

  void setGender(final Gender pGender);

  void setAddress(final String pAddress);

  void setCountry(final Country pCountry);

  void setCategory(final ContributorCategory pContributorCategory);

  void setRefUserId(final String pRefUserId);
}
