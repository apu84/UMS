package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableCheckIn;

import java.io.Serializable;
import java.util.Date;

public interface CheckIn extends Serializable, LastModifier, EditType<MutableCheckIn>, Identifier<Long> {

  Long getCheckoutId();

  Date getReturnDate();
}
