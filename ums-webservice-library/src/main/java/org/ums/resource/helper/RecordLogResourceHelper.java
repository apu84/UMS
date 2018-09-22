package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.RecordLogBuilder;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.library.RecordLogManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class RecordLogResourceHelper extends ResourceHelper<RecordLog, MutableRecordLog, Long> {

  @Autowired
  private RecordLogManager mManager;

  @Autowired
  private RecordLogBuilder mBuilder;

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  public JsonObject get(final String pDate, final String pEmployeeId, final String pMfn, final UriInfo pUriInfo) {
    String clause = "";
    clause += (pDate.isEmpty() ? "" : " MODIFIED_ON = " + mDateFormat.parse(pDate));
    clause += (pDate.isEmpty() && pEmployeeId.isEmpty()) || (pDate.isEmpty() && pMfn.isEmpty()) ? "" : " AND ";
    clause += (pEmployeeId.isEmpty() ? "" : " MODIFIED_BY = " + pEmployeeId);
    clause += (pDate.isEmpty() && pEmployeeId.isEmpty()) || (pDate.isEmpty() && pMfn.isEmpty()) ? "" : " AND ";
    clause += (pMfn.isEmpty() ? "" : " MFN = " + Long.parseLong(pMfn));
   // List<RecordLog> recordLogs = mManager.getAll();

    List<RecordLog> recordLogs = mManager.get(clause);
    return buildJsonResponse(recordLogs, pUriInfo);
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<RecordLog, MutableRecordLog, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<RecordLog, MutableRecordLog> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(RecordLog pReadonly) {
    return pReadonly.getLastModified();
  }
}
