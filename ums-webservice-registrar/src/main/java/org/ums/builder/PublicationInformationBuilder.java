package org.ums.builder;

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
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    if(pReadOnly.getPublicationTitle() != null) {
      pBuilder.add("publicationTitle", pReadOnly.getPublicationTitle());
    }
    else {
      pBuilder.add("publicationTitle", "");
    }
    pBuilder.add("publicationInterestGenre", pReadOnly.getInterestGenre());
    pBuilder.add("publisherName", pReadOnly.getPublisherName());
    pBuilder.add("dateOfPublication", pReadOnly.getDateOfPublication());
    pBuilder.add("publicationType", pReadOnly.getPublicationType());
    pBuilder.add("publicationWebLink", pReadOnly.getPublicationWebLink());
  }

  @Override
  public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(!pJsonObject.getString("publicationTitle").equals("")) {
      pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
    }
    else {
      pMutable.setPublicationTitle("");
    }
    if(!pJsonObject.getString("publicationInterestGenre").equals("")) {
      pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
    }
    else {
      pMutable.setInterestGenre("");
    }
    if(!pJsonObject.getString("publisherName").equals("")) {
      pMutable.setPublisherName(pJsonObject.getString("publisherName"));
    }
    else {
      pMutable.setPublisherName("");
    }
    if(!pJsonObject.getString("dateOfPublication").equals("")) {
      pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
    }
    else {
      pMutable.setDateOfPublication("");
    }
    if(pJsonObject.getJsonObject("publicationType") != null) {
      pMutable.setPublicationType(pJsonObject.getJsonObject("publicationType").getString("name"));
    }
    if(!pJsonObject.getString("publicationWebLink").equals("")) {
      pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
    }
    else {
      pMutable.setPublicationWebLink("");
    }
  }
}
