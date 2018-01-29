package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableReceipt;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface Receipt
    extends
    Serializable,
    EditType<MutableReceipt>,
    LastModifier,
    Identifier<Long> {

  String getName();

  String getShortName();
}