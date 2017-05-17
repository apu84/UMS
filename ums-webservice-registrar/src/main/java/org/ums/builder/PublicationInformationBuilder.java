package org.ums.builder;

import jdk.nashorn.internal.scripts.JO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class PublicationInformationBuilder implements Builder<PublicationInformation, MutablePublicationInformation> {

  @Autowired
  UserManager userManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, PublicationInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("rowId", pReadOnly.getRowId());
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
  }

  @Override
  public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
    pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
    pMutable.setPublisherName(pJsonObject.getString("publisherName"));
    pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
    pMutable.setPublicationType(pJsonObject.getJsonObject("publicationType").getString("name"));
    pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
    pMutable.setPublicationISSN(pJsonObject.getString("publicationISSN"));
    pMutable.setPublicationIssue(pJsonObject.getString("publicationIssue"));
    pMutable.setPublicationVolume(pJsonObject.getString("publicationVolume"));
    pMutable.setPublicationJournalName(pJsonObject.getString("publicationJournalName"));
    pMutable.setPublicationCountry(pJsonObject.getString("publicationCountry"));
    pMutable.setPublicationPages(pJsonObject.getString("publicationPages"));
    pMutable.setPublicationStatus(pJsonObject.getString("status"));
    // pMutable.setPublicationStatus("1");
  }

  public void updatePublicationInformationBuilder(MutablePublicationInformation pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setRowId(pJsonObject.getInt("rowId"));
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
    // pMutable.setPublicationStatus("1");
  }
}
