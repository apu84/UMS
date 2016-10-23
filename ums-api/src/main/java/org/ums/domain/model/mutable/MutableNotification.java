package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;

import java.util.Date;

public interface MutableNotification extends Notification, Mutable, MutableIdentifier<String>,
    MutableLastModifier {
  void setProducerId(String pProducerId);

  void setConsumerId(String pConsumerId);

  void setNotificationType(String pNotificationType);

  void setPayload(String pPayload);

  void setProducedOn(Date pProducedOn);

  void setConsumedOn(Date pConsumedOn);
}
