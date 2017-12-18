package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableCheckout;

import java.io.Serializable;
import java.util.Date;

public interface Checkout extends Serializable, LastModifier, EditType<MutableCheckout>, Identifier<Long> {

  String getPatronId();

  Long getMfn();

  Date getIssueDate();

  Date getDueDate();
}
