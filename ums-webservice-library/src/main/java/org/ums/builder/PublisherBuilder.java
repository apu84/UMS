package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.library.PublisherManager;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class PublisherBuilder implements Builder<Publisher, MutablePublisher> {

  @Autowired
  PublisherManager mPublisherManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Publisher pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("text", pReadOnly.getName());
    pBuilder.add("countryId", pReadOnly.getCountryId());
    pBuilder.add("countryName", mPublisherManager.get(pReadOnly.getCountryId()).getName());
    pBuilder.add("contactPerson", pReadOnly.getContactPerson());
    pBuilder.add("phoneNumber", pReadOnly.getPhoneNumber());
    pBuilder.add("emailAddress", pReadOnly.getEmailAddress());
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));

  }

  @Override
  public void build(final MutablePublisher pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    pMutable.setId(pJsonObject.getInt("id"));
    pMutable.setName(pJsonObject.getString("name"));
    pMutable.setCountryId(pJsonObject.getInt("countryId"));
    pMutable.setContactPerson(pJsonObject.getString("contactPerson"));
    pMutable.setPhoneNumber(pJsonObject.getString("phoneNumber"));
    pMutable.setEmailAddress(pJsonObject.getString("emailAddress"));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
