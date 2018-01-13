package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableFine;

import java.io.Serializable;
import java.util.Date;

public interface Fine extends Serializable, LastModifier, EditType<MutableFine>, Identifier<Long> {

  Long getCirculationId();

  int getFineCategory();

  Date getFineAppliedDate();

  String getFineAppliedBy();

  String getFineForgivenBy();

  Date getFinePaymentDate();

  String getDescription();

  double getAmount();

  String getPatronId();
}
