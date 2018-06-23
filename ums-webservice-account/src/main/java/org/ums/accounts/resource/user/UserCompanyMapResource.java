package org.ums.accounts.resource.user;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.CacheFactory;
import org.ums.cache.GenericCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;
import org.ums.manager.CompanyManager;
import org.ums.manager.UserCompanyMapManager;
import org.ums.resource.Resource;
import org.ums.util.Utils;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/account/user-company-map")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class UserCompanyMapResource {

  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  UserCompanyMapManager userCompanyMapManager;

  @Autowired
  CompanyManager mCompanyManager;

  @GET
  @Path("/user")
  public List<Company> getCompanyList() {
    List<UserCompanyMap> list = userCompanyMapManager.getCompanyList(SecurityUtils.getSubject().getPrincipal().toString());
    List<Company> companyList = new ArrayList<>();
    list.forEach(e->companyList.add(mCompanyManager.get(e.getCompanyId())));
    return companyList;
  }

  @POST
  @Path("/company/{company-id}")
  public String setCompany(@PathParam("company-id") String companyId) {
    String cacheKey = SecurityUtils.getSubject().getPrincipal().toString() + "-company";
    GenericCache accountCache = new GenericCache(mCacheFactory.getCacheManager());
    accountCache.putObject(cacheKey, mCompanyManager.get(companyId));
    return "{\"status\":\"success\"}";
  }

  @GET
  @Path("/company")
  public String getCompany() {
    Company company = Utils.getCompany();
    return "{\"company\":\"" + company == null ? "" : company.getName() + "\"}";
  }

}
