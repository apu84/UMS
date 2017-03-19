package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Ifti on 30-Jan-17.
 */
public interface MutableCountry extends Country, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

  void setCode(final String pCode);

  void setName(final String pName);
}
