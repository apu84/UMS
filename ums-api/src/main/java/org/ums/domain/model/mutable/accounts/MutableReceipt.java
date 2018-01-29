package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface MutableReceipt extends Receipt, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setName(String pName);

  void setShortName(String pShortName);
}
