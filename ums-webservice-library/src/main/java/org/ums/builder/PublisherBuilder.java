package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.common.CountryManager;
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

  @Autowired
  CountryManager mCountryManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Publisher pReadOnly, UriInfo pUriInfo,
                    final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("text", pReadOnly.getName());
    pBuilder.add("countryId", pReadOnly.getCountryId());
    pBuilder.add("countryName", pReadOnly.getCountryId() == 0 ? "" : mCountryManager.get(pReadOnly.getCountryId())
        .getName());
    pBuilder.add("contactPerson", UmsUtils.nullConversion(pReadOnly.getContactPerson()));
    pBuilder.add("phoneNumber", UmsUtils.nullConversion(pReadOnly.getPhoneNumber()));
    pBuilder.add("emailAddress", UmsUtils.nullConversion(pReadOnly.getEmailAddress()));
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));

  }

  @Override
  public void build(final MutablePublisher pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    if (pJsonObject.containsKey("id"))
      pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
    pMutable.setName(pJsonObject.getString("name"));
    if (pJsonObject.containsKey("countryId"))
      pMutable.setCountryId(pJsonObject.getInt("countryId"));
    if (pJsonObject.containsKey("contactPerson"))
      pMutable.setContactPerson(pJsonObject.getString("contactPerson"));
    if (pJsonObject.containsKey("phoneNumber"))
      pMutable.setPhoneNumber(pJsonObject.getString("phoneNumber"));
    if (pJsonObject.containsKey("emailAddress"))
      pMutable.setEmailAddress(pJsonObject.getString("emailAddress"));
  }
}
