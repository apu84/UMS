package org.ums.builder.employee;


import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.Employee.PublicationInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePublicationInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class PublicationInformationBuilder implements Builder<PublicationInformation, MutablePublicationInformation> {
    @Override
    public void build(JsonObjectBuilder pBuilder, PublicationInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
        pBuilder.add("employeeId", pReadOnly.getEmployeeId());
        pBuilder.add("publicationTitle", pReadOnly.getPublicationTitle());
        pBuilder.add("publicationInterestGenre", pReadOnly.getInterestGenre());
        pBuilder.add("authorsName", pReadOnly.getAuthor());
        pBuilder.add("publisherName", pReadOnly.getPublisherName());
        pBuilder.add("dateOfPublication", pReadOnly.getDateOfPublication());
        pBuilder.add("publicationType", pReadOnly.getPublicationType());
        pBuilder.add("publicationWebLink", pReadOnly.getPublicationWebLink());
    }

    @Override
    public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
        pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
        pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
        pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
        pMutable.setAuthor(pJsonObject.getString("authorsName"));
        pMutable.setPublisherName(pJsonObject.getString("publisherName"));
        pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
        pMutable.setPublicationType(pJsonObject.getString("publicationType"));
        pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
    }
}
