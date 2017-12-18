package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableFine;

import java.io.Serializable;

public interface Fine extends Serializable, LastModifier, EditType<MutableFine>, Identifier<Long> {

  Long getCheckInId();

  int getFineCategory();

  String getDescription();

  double getAmount();
}
