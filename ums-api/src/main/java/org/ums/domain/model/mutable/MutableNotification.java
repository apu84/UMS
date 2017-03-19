package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Notification;

import java.util.Date;

public interface MutableNotification extends Notification, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {
  void setProducerId(String pProducerId);

  void setConsumerId(String pConsumerId);

  void setNotificationType(String pNotificationType);

  void setPayload(String pPayload);

  void setProducedOn(Date pProducedOn);

  void setConsumedOn(Date pConsumedOn);
}
