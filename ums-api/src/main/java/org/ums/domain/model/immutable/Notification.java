package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableNotification;

import java.io.Serializable;
import java.util.Date;

public interface Notification extends Serializable, EditType<MutableNotification>, LastModifier, Identifier<String> {
  String getProducerId();

  String getConsumerId();

  String getNotificationType();

  String getPayload();

  Date getProducedOn();

  Date getConsumedOn();
}
