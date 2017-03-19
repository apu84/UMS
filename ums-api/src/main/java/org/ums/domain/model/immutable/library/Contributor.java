package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.enums.common.Gender;
import org.ums.enums.library.ContributorCategory;

import java.io.Serializable;

/**
 * Represents Mst_Contributor
 */
public interface Contributor extends Serializable, LastModifier, EditType<MutableContributor>, Identifier<Integer> {

  String getFirstName();

  String getMiddleName();

  String getLastName();

  String getShortName();

  Gender getGender();

  String getAddress();

  Country getCountry();

  ContributorCategory getCategory();

  String getRefUserId();

}
