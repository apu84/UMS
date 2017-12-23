package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.enums.library.MaterialType;
import org.ums.formatter.DateFormat;
import org.ums.manager.library.RecordManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Date;

@Component
public class CirculationBuilder implements Builder<Circulation, MutableCirculation> {

  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  private RecordManager mRecordManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Circulation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    Record record = mRecordManager.get(pReadOnly.getMfn());
    pBuilder.add("circulationId", pReadOnly.getId().toString());
    pBuilder.add("patronId", pReadOnly.getPatronId());
    pBuilder.add("mfn", pReadOnly.getMfn().toString());
    pBuilder.add("issueDate", mDateFormat.format(pReadOnly.getIssueDate()));
    pBuilder.add("dueDate", mDateFormat.format(pReadOnly.getDueDate()));
    pBuilder.add("returnDate", pReadOnly.getReturnDate() == null ? "" : mDateFormat.format(pReadOnly.getReturnDate()));
    pBuilder.add("fineStatus", pReadOnly.getFineStatus());
    pBuilder.add("fineStatusString", pReadOnly.getFineStatus() == 0 ? "No Fine Applied" : "Fine Applied");
    pBuilder.add("title", record.getTitle());
    pBuilder.add("materialType", (record.getMaterialType()).getLabel());
    pBuilder.add("checkBoxStatus", false);
  }

  @Override
  public void build(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.containsKey("circulationId") ? Long.parseLong(pJsonObject.getString("circulationId"))
        : null);
    pMutable.setPatronId(pJsonObject.getString("patronId"));
    pMutable.setMfn(Long.parseLong(pJsonObject.getString("mfn")));
    pMutable
        .setIssueDate(pJsonObject.containsKey("issueDate") ? pJsonObject.getString("issueDate") == null ? new Date()
            : mDateFormat.parse(pJsonObject.getString("issueDate")) : null);
    pMutable.setDueDate(mDateFormat.parse(pJsonObject.getString("dueDate")));
    pMutable.setFineStatus(pJsonObject.containsKey("fineStatus") ? pJsonObject.getInt("fineStatus") : 0);

    pMutable.setReturnDate(pJsonObject.containsKey("returnDate") ? pJsonObject.getString("returnDate") == null
        || pJsonObject.getString("returnDate").equals("") ? null : mDateFormat.parse(pJsonObject
        .getString("returnDate")) : null);

    if(pJsonObject.containsKey("checkBoxStatus")) {
      if(pJsonObject.getBoolean("checkBoxStatus")) {
        pMutable.setReturnDate(new Date());
      }
    }
  }

  public void checkInBuilder(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setReturnDate(pJsonObject.containsKey("returnDate") ? mDateFormat.parse(pJsonObject
        .getString("returnDate")) : new Date());
    pMutable.setMfn(Long.parseLong(pJsonObject.getString("mfn")));
  }
}
