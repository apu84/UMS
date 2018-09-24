package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class RecordLogBuilder implements Builder<RecordLog, MutableRecordLog> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, RecordLog pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("mfn", pReadOnly.getMfn());
    pBuilder.add("modifiedOn", mDateFormat.format(pReadOnly.getModifiedOn()));
    pBuilder.add("modifiedBy", pReadOnly.getModifiedBy());
    pBuilder.add("modificationType", pReadOnly.getModificationType());
    pBuilder.add("previousJson", pReadOnly.getPreviousJson() == null ? "" : pReadOnly.getPreviousJson());
    pBuilder.add("modifiedJson", pReadOnly.getModifiedJson() == null ? "" : pReadOnly.getModifiedJson());
  }

  @Override
  public void build(MutableRecordLog pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
