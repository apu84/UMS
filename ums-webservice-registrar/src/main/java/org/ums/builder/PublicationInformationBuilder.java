package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PublicationInformationBuilder implements Builder<PublicationInformation, MutablePublicationInformation> {

  Date date = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  String mDate = sdf.format(date);

  @Override
  public void build(JsonObjectBuilder pBuilder, PublicationInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("publicationTitle", pReadOnly.getPublicationTitle());
    if(pReadOnly.getInterestGenre() != null) {
      pBuilder.add("publicationInterestGenre", pReadOnly.getInterestGenre());
    }
    else {
      pBuilder.add("publicationInterestGenre", "");
    }
    if(pReadOnly.getPublisherName() != null) {
      pBuilder.add("publisherName", pReadOnly.getPublisherName());
    }
    else {
      pBuilder.add("publisherName", "");
    }
    pBuilder.add("dateOfPublication", pReadOnly.getDateOfPublication());
    pBuilder.add("publicationType", pReadOnly.getPublicationType());
    if(pReadOnly.getPublicationWebLink() != null) {
      pBuilder.add("publicationWebLink", pReadOnly.getPublicationWebLink());
    }
    else {
      pBuilder.add("publicationWebLink", "");
    }

    if(pReadOnly.getPublicationISSN() != null) {
      pBuilder.add("publicationISSN", pReadOnly.getPublicationISSN());
    }
    else {
      pBuilder.add("publicationISSN", "");
    }
    if(pReadOnly.getPublicationIssue() != null) {
      pBuilder.add("publicationIssue", pReadOnly.getPublicationIssue());
    }
    else {
      pBuilder.add("publicationIssue", "");
    }
    if(pReadOnly.getPublicationVolume() != null) {
      pBuilder.add("publicationVolume", pReadOnly.getPublicationVolume());
    }
    else {
      pBuilder.add("publicationVolume", "");
    }
    if(pReadOnly.getPublicationJournalName() != null) {
      pBuilder.add("publicationJournalName", pReadOnly.getPublicationJournalName());
    }
    else {
      pBuilder.add("publicationJournalName", "");
    }
    if(pReadOnly.getPublicationCountry() != null) {
      pBuilder.add("publicationCountry", pReadOnly.getPublicationCountry());
    }
    else {
      pBuilder.add("publicationCountry", "");
    }

    pBuilder.add("status", pReadOnly.getPublicationStatus());

    if(pReadOnly.getPublicationPages() != null) {
      pBuilder.add("publicationPages", pReadOnly.getPublicationPages());
    }
    else {
      pBuilder.add("publicationPages", "");
    }
    pBuilder.add("appliedOn", pReadOnly.getAppliedOn());

    if(pReadOnly.getActionTakenOn() != null) {
      pBuilder.add("actionTakenOn", pReadOnly.getActionTakenOn());
    }
    else {
      pBuilder.add("actionTakenOn", "");
    }

    if(pReadOnly.getRowNumber() != 0) {
      pBuilder.add("rowNumber", pReadOnly.getRowNumber());
    }
    else {
      pBuilder.add("rowNumber", "");
    }
  }

  @Override
  public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("dbAction")) {
      if(pJsonObject.getString("dbAction").equals("Update")) {
        pMutable.setId(pJsonObject.getInt("id"));
      }
      else if(pJsonObject.getString("dbAction").equals("Create")) {
      }
      else if(pJsonObject.getString("dbAction").equals("Delete")) {
        pMutable.setId(pJsonObject.getInt("id"));
      }
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));

      pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
      if(!pJsonObject.containsKey("publicationInterestGenre")) {
        pMutable.setInterestGenre("");
      }
      else {
        pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
      }
      if(!pJsonObject.containsKey("publisherName")) {
        pMutable.setPublisherName("");
      }
      else {
        pMutable.setPublisherName(pJsonObject.getString("publisherName"));
      }
      if(!pJsonObject.containsKey("publicationWebLink")) {
        pMutable.setPublicationWebLink("");
      }
      else {
        pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
      }
      if(!pJsonObject.containsKey("publicationISSN")) {
        pMutable.setPublicationISSN("");
      }
      else {
        pMutable.setPublicationISSN(pJsonObject.getString("publicationISSN"));
      }
      if(!pJsonObject.containsKey("publicationIssue")) {
        pMutable.setPublicationIssue("");
      }
      else {
        pMutable.setPublicationIssue(pJsonObject.getString("publicationIssue"));
      }
      if(!pJsonObject.containsKey("publicationVolume")) {
        pMutable.setPublicationVolume("");
      }
      else {
        pMutable.setPublicationVolume(pJsonObject.getString("publicationVolume"));
      }
      if(!pJsonObject.containsKey("publicationJournalName")) {
        pMutable.setPublicationJournalName("");
      }
      else {
        pMutable.setPublicationJournalName(pJsonObject.getString("publicationJournalName"));
      }
      if(!pJsonObject.containsKey("publicationCountry")) {
        pMutable.setPublicationCountry("");
      }
      else {
        pMutable.setPublicationCountry(pJsonObject.getString("publicationCountry"));
      }
      if(!pJsonObject.containsKey("publicationPages")) {
        pMutable.setPublicationPages("");
      }
      else {
        pMutable.setPublicationPages(pJsonObject.getString("publicationPages"));
      }
      pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
      pMutable.setPublicationType(pJsonObject.getJsonObject("publicationType").getString("name"));
      // pMutable.setPublicationStatus(pJsonObject.getString("status"));
      pMutable.setAppliedOn(mDate);
      pMutable.setPublicationStatus("0");
      pMutable.setActionTakenOn("");
    }
  }

  public void updatePublicationInformationBuilder(MutablePublicationInformation pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setId(pJsonObject.getInt("id"));
    pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
    pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
    pMutable.setPublisherName(pJsonObject.getString("publisherName"));
    pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
    pMutable.setPublicationType(pJsonObject.getString("publicationType"));
    pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
    pMutable.setPublicationISSN(pJsonObject.getString("publicationISSN"));
    pMutable.setPublicationIssue(pJsonObject.getString("publicationIssue"));
    pMutable.setPublicationVolume(pJsonObject.getString("publicationVolume"));
    pMutable.setPublicationJournalName(pJsonObject.getString("publicationJournalName"));
    pMutable.setPublicationCountry(pJsonObject.getString("publicationCountry"));
    pMutable.setPublicationPages(pJsonObject.getString("publicationPages"));
    pMutable.setPublicationStatus(pJsonObject.getString("status"));
    pMutable.setAppliedOn(pJsonObject.getString("appliedOn"));
    pMutable.setActionTakenOn(mDate);
  }
}
