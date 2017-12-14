package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
public interface MutableGroup extends Group, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

}
