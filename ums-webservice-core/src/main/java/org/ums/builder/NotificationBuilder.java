package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class NotificationBuilder implements Builder<Notification, MutableNotification> {
  protected static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss Z";
  protected static DateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);

  @Override
  public void build(JsonObjectBuilder pBuilder, Notification pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("payload", pReadOnly.getPayload());
    if(pReadOnly.getProducedOn() != null) {
      pBuilder.add("producedOn", mDateFormat.format(pReadOnly.getProducedOn()));
    }
    if(pReadOnly.getConsumedOn() != null) {
      pBuilder.add("consumedOn", mDateFormat.format(pReadOnly.getConsumedOn()));
    }
  }

  @Override
  public void build(MutableNotification pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {

  }
}
