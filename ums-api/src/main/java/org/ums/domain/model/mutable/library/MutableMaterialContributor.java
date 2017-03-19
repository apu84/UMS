package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.library.ContributorRole;

/**
 * Created by Ifti on 16-Feb-17.
 */
public interface MutableMaterialContributor extends MaterialContributor, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setMfn(final String pMfn);

  void setViewOrder(final Integer pViewOrder);

  void setContributorRole(final ContributorRole pContributorRole);

  void setContributor(final Contributor pContributor);
}
