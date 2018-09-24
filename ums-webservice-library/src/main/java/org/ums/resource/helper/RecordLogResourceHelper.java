package org.ums.resource.helper;

import org.jetbrains.annotations.NotNull;
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
import org.ums.util.Constants;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
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
    String clause = buildClause(pDate, pEmployeeId, pMfn);
    List<RecordLog> recordLogs = recordLogs = mManager.get(clause);
    return buildJsonResponse(recordLogs, pUriInfo);
  }

  @NotNull
  private String buildClause(String pDate, String pEmployeeId, String pMfn) {

    List<String> clauseList = new ArrayList<>();

    if(!pDate.isEmpty()) {
      clauseList.add(" MODIFIED_ON = TO_DATE( " + "'" + pDate + "'" + "," + "'" + Constants.DATE_FORMAT + "'" + " ) ");
    }

    if(!pEmployeeId.isEmpty()) {
      clauseList.add(" MODIFIED_BY = " + pEmployeeId);
    }

    if(!pMfn.isEmpty()) {
      clauseList.add(" MFN = " + Long.parseLong(pMfn));
    }

    String clause = "";

    for(int i = 0; i < clauseList.size(); i++) {
      clause += clauseList.get(i);
      clause += !(clauseList.size() - 1 == i) ? " AND " : "";
    }

    return clause.isEmpty() ? "" : " WHERE " + clause;
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
