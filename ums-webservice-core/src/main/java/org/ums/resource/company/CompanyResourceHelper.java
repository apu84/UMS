package org.ums.resource.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.persistent.model.PersistentCompany;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jul-18.
 */
@Component
public class CompanyResourceHelper extends ResourceHelper<Company, MutableCompany, String> {

  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private CompanyBuilder mCompanyBuilder;
  @Autowired
  private IdGenerator mIdGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableCompany company = new PersistentCompany();
    LocalCache localCache = new LocalCache();
    getBuilder().build(company, pJsonObject, localCache);
    company.setId(mIdGenerator.getAlphaNumericId("", 2));
    getContentManager().create(company);
    return Response.accepted().build();
  }

  public JsonObject save(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableCompany company = new PersistentCompany();
    LocalCache localCache = new LocalCache();
    getBuilder().build(company, pJsonObject, localCache);
    company.setId(mIdGenerator.getAlphaNumericId("", 8));
    getContentManager().create(company);
    return getCompany(company.getId(), pUriInfo);
  }

  public JsonObject put(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableCompany company = new PersistentCompany();
    LocalCache localCache = new LocalCache();
    getBuilder().build(company, pJsonObject, localCache);
    getContentManager().update(company);
    return getCompany(company.getId(), pUriInfo);
  }

  public JsonArray getAllCompany(UriInfo pUriInfo) {
    List<Company> companyList = getContentManager().getAll();
    JsonArrayBuilder companyJsonArray = Json.createArrayBuilder();
    for(Company company : companyList) {
      LocalCache localCache = new LocalCache();
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      getBuilder().build(jsonObjectBuilder, company, pUriInfo, localCache);
      companyJsonArray.add(jsonObjectBuilder);
    }
    return companyJsonArray.build();
  }

  public JsonObject getCompany(String pCompanyId, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    Company company = getContentManager().get(pCompanyId);
    getBuilder().build(jsonObjectBuilder, company, pUriInfo, localCache);
    return jsonObjectBuilder.build();
  }

  @Override
  protected CompanyManager getContentManager() {
    return mCompanyManager;
  }

  @Override
  protected Builder<Company, MutableCompany> getBuilder() {
    return mCompanyBuilder;
  }

  @Override
  protected String getETag(Company pReadonly) {
    return pReadonly.getLastModified();
  }
}
