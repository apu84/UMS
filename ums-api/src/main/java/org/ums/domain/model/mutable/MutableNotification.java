package org.ums.domain.model.mutable;


import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;

import java.util.Date;

public interface MutableNotification extends Notification, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setProducerId(String pProducerId);

  void setProducer(User pProducer);

  void setConsumerId(String pConsumerId);

  void setConsumer(User pConsumer);

  void setNotificationType(String pNotificationType);

  void setPayload(String pPayload);

  void setProducedOn(Date pProducedOn);

  void setConsumedOn(Date pConsumedOn);
}
