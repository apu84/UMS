package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableMonth;

import java.io.Serializable;

public interface Month extends Serializable, EditType<MutableMonth>, LastModifier, Identifier<Long> {

  String getName();

  String getShortName();
}
