package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableMaterialContributor;
import org.ums.enums.library.ContributorRole;

import java.io.Serializable;

/**
 * Represents Contributor Table
 */
public interface MaterialContributor extends Serializable, LastModifier, EditType<MutableMaterialContributor>,
    Identifier<Integer> {

  String getMfn();

  Integer getViewOrder();

  ContributorRole getContributorRole();

  Contributor getContributor();

}
