package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.DepartmentManager;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class SupplierBuilder implements Builder<Supplier, MutableSupplier> {

  @Autowired
  DepartmentManager mDepartmentManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Supplier pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("text", pReadOnly.getName());
    pBuilder.add("address", UmsUtils.nullConversion(pReadOnly.getAddress()));
    pBuilder.add("contactPerson", UmsUtils.nullConversion(pReadOnly.getContactPerson()));
    pBuilder.add("contactNumber", UmsUtils.nullConversion(pReadOnly.getContactNumber()));
    pBuilder.add("email", UmsUtils.nullConversion(pReadOnly.getEmail()));
    pBuilder.add("note", UmsUtils.nullConversion(pReadOnly.getNote()));
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));

  }

  @Override
  public void build(final MutableSupplier pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
    pMutable.setName(pJsonObject.getString("name"));

    if(pJsonObject.containsKey("address"))
      pMutable.setAddress(pJsonObject.getString("address"));
    if(pJsonObject.containsKey("note"))
      pMutable.setNote(pJsonObject.getString("note"));
    if(pJsonObject.containsKey("email"))
      pMutable.setEmail(pJsonObject.getString("email"));
    if(pJsonObject.containsKey("contactPerson"))
      pMutable.setContactPerson(pJsonObject.getString("contactPerson"));
    if(pJsonObject.containsKey("contactNumber"))
      pMutable.setContactNumber(pJsonObject.getString("contactNumber"));
    // pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
