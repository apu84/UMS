package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Parameter;

/**
 * Created by My Pc on 3/13/2016.
 */
public interface MutableParameter extends Parameter, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setParameter(String pParameter);

  void setShortDescription(String pShortDescription);

  void setLongDescription(String pDescription);

  void setType(int pType);
}
