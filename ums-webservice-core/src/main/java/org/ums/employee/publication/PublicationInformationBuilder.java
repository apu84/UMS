package org.ums.employee.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.PublicationType;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.CountryManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PublicationInformationBuilder implements Builder<PublicationInformation, MutablePublicationInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  PublicationType mPublicationType;

  @Autowired
  CountryManager mCountryManager;

  Date date = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  String mDate = sdf.format(date);

  @Override
  public void build(JsonObjectBuilder pBuilder, PublicationInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("publicationTitle", pReadOnly.getTitle());
    pBuilder.add("publicationInterestGenre", pReadOnly.getInterestGenre() == null ? "" : pReadOnly.getInterestGenre());
    pBuilder.add("publisherName", pReadOnly.getPublisherName() == null ? "" : pReadOnly.getPublisherName());
    pBuilder.add("dateOfPublication", pReadOnly.getDateOfPublication());
    JsonObjectBuilder publicationTypeBuilder = Json.createObjectBuilder();
    publicationTypeBuilder.add("id", pReadOnly.getTypeId()).add("name",
        mPublicationType.get(pReadOnly.getTypeId()).getLabel());
    pBuilder.add("publicationType", publicationTypeBuilder);
    pBuilder.add("publicationWebLink", pReadOnly.getWebLink() == null ? "" : pReadOnly.getWebLink());
    pBuilder.add("publicationISSN", pReadOnly.getISSN() == null ? "" : pReadOnly.getISSN());
    pBuilder.add("publicationIssue", pReadOnly.getIssue() == null ? "" : pReadOnly.getIssue());
    pBuilder.add("publicationVolume", pReadOnly.getVolume() == null ? "" : pReadOnly.getVolume());
    pBuilder.add("publicationJournalName", pReadOnly.getJournalName() == null ? "" : pReadOnly.getJournalName());
    if(pReadOnly.getCountryId() == 0) {
      pBuilder.add("publicationCountry", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder countryBuilder = Json.createObjectBuilder();
      countryBuilder.add("id", pReadOnly.getCountryId()).add("name",
          mCountryManager.get(pReadOnly.getCountryId()).getName());
      pBuilder.add("publicationCountry", countryBuilder);
    }
    pBuilder.add("status", pReadOnly.getStatus());
    pBuilder.add("publicationPages", pReadOnly.getPages() == null ? "" : pReadOnly.getPages());
    pBuilder.add("appliedOn", mDateFormat.format(pReadOnly.getAppliedOn()));
    pBuilder.add("actionTakenOn",
        pReadOnly.getActionTakenOn() == null ? "" : mDateFormat.format(pReadOnly.getActionTakenOn()));
    pBuilder.add("rowNumber", pReadOnly.getRowNumber());
  }

  @Override
  public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setTitle(pJsonObject.getString("publicationTitle"));
    pMutable.setInterestGenre(pJsonObject.containsKey("publicationInterestGenre") ? pJsonObject
        .getString("publicationInterestGenre") : "");
    pMutable.setPublisherName(pJsonObject.containsKey("publisherName") ? pJsonObject.getString("publisherName") : "");
    pMutable.setWebLink(pJsonObject.containsKey("publicationWebLink") ? pJsonObject.getString("publicationWebLink")
        : "");
    pMutable.setISSN(pJsonObject.containsKey("publicationISSN") ? pJsonObject.getString("publicationISSN") : "");
    pMutable.setIssue(pJsonObject.containsKey("publicationIssue") ? pJsonObject.getString("publicationIssue") : "");
    pMutable.setVolume(pJsonObject.containsKey("publicationVolume") ? pJsonObject.getString("publicationVolume") : "");
    pMutable.setJournalName(pJsonObject.containsKey("publicationJournalName") ? pJsonObject
        .getString("publicationJournalName") : "");
    pMutable.setCountry(pJsonObject.containsKey("publicationCountry") ? pJsonObject.isNull("publicationCountry") ? null
        : mCountryManager.get(pJsonObject.getJsonObject("publicationCountry").getInt("id")) : null);
    pMutable.setPages(pJsonObject.containsKey("publicationPages") ? pJsonObject.getString("publicationPages") : "");

    pMutable.setDateOfPublication(pJsonObject.getInt("dateOfPublication"));
    pMutable.setTypeId(pJsonObject.getJsonObject("publicationType").getInt("id"));
    pMutable.setAppliedOn(mDateFormat.parse(mDate));
    pMutable.setStatus(pJsonObject.getString("status"));
    pMutable.setActionTakenOn(null);
  }
}
