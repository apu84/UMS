package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.enums.library.MaterialType;
import org.ums.formatter.DateFormat;
import org.ums.manager.library.CirculationManager;
import org.ums.manager.library.FineManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentCirculation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class CirculationBuilder implements Builder<Circulation, MutableCirculation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Autowired
  private RecordManager mRecordManager;

  @Autowired
  private FineManager mFineManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Circulation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    Record record = mRecordManager.get(pReadOnly.getMfn());
    if(new Date().compareTo(pReadOnly.getDueDate()) > -1) {
      pBuilder.add("overDueStatus", true);
    }
    else {
      pBuilder.add("overDueStatus", false);
    }
    pBuilder.add("circulationId", pReadOnly.getId().toString());
    pBuilder.add("patronId", pReadOnly.getPatronId());
    pBuilder.add("mfn", pReadOnly.getMfn().toString());
    pBuilder.add("itemCode", pReadOnly.getAccessionNumber());
    pBuilder.add("issueDate", mDateFormat.format(pReadOnly.getIssueDate()));
    java.text.DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa");
    String outputString = outputFormat.format(pReadOnly.getDueDate());
    pBuilder.add("dueDate", outputString);
    if(pReadOnly.getReturnDate() == null) {
      pBuilder.add("returnDate", "");
    }
    else {
      java.text.DateFormat outputReturnDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa");
      String outputReturnDateString = outputReturnDateFormat.format(pReadOnly.getReturnDate());
      pBuilder.add("returnDate", outputReturnDateString);
    }
    pBuilder.add("fineStatus", pReadOnly.getFineStatus());
    pBuilder.add("fineStatusString", pReadOnly.getFineStatus() == 0 ? "No Fine Applied" : "Fine Applied");
    pBuilder.add("title", record.getTitle());
    pBuilder.add("materialType", (record.getMaterialType()).getLabel());
    pBuilder.add("checkBoxStatus", false);
    pBuilder.add("totalItems", record.getTotalItems());
    pBuilder.add("totalAvailable", record.getTotalAvailable());
    pBuilder.add("totalCheckedOut", record.getTotalCheckedOut());
    pBuilder.add("totalOnHold", record.getTotalOnHold());

    if(pReadOnly.getFineStatus() == 1) {
      MutableFine mutableFine = (MutableFine) mFineManager.getFine(pReadOnly.getId());
      pBuilder.add("amount", mutableFine.getAmount());
    }
  }

  @Override
  public void build(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    java.text.DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    Date date = null;
    try {
      date = dateFormat.parse(pJsonObject.getString("dueDate"));
    } catch(ParseException e) {
      e.printStackTrace();
    }
    long time = date.getTime();

    pMutable.setId(pJsonObject.containsKey("circulationId") ? Long.parseLong(pJsonObject.getString("circulationId"))
        : null);
    pMutable.setPatronId(pJsonObject.getString("patronId"));
    pMutable.setMfn(Long.parseLong(pJsonObject.getString("mfn")));
    pMutable.setIssueDate(new Date());
    pMutable.setDueDate(new Timestamp(time));
    pMutable.setFineStatus(pJsonObject.containsKey("fineStatus") ? pJsonObject.getInt("fineStatus") : 0);
    pMutable.setReturnDate(pJsonObject.containsKey("returnDate") ? pJsonObject.getString("returnDate") == null
        || pJsonObject.getString("returnDate").equals("") ? null : mDateFormat.parse(pJsonObject
        .getString("returnDate")) : null);

    if(pJsonObject.containsKey("checkBoxStatus")) {
      if(pJsonObject.getBoolean("checkBoxStatus")) {
        pMutable.setReturnDate(new Date());
      }
    }

    pMutable.setAccessionNumber(pJsonObject.getString("itemCode"));
  }

  public void checkInBuilder(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    java.text.DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    Date date = null;
    try {
      date = dateFormat.parse(pJsonObject.getString("returnDate"));
    } catch(ParseException e) {
      e.printStackTrace();
    }
    long time = date.getTime();
    pMutable.setReturnDate(new Timestamp(time));
    pMutable.setAccessionNumber(pJsonObject.getString("itemCode"));
    if(pJsonObject.containsKey("fineStatus")) {
      if(pJsonObject.getBoolean("fineStatus")) {
        pMutable.setFineStatus(2);
      }
      else {
        pMutable.setFineStatus(1);
      }
    }
    else {
      pMutable.setFineStatus(0);
    }
  }

  public void checkInUpdateBuilder(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setReturnDate(new Date());
    pMutable.setAccessionNumber(pJsonObject.getString("itemCode"));
    pMutable.setId(Long.parseLong(pJsonObject.getString("circulationId")));
    pMutable.setPatronId(pJsonObject.getString("patronId"));
  }
}
