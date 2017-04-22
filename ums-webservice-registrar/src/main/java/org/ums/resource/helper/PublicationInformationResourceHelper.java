package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PublicationInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.manager.registrar.PublicationInformationManager;
import org.ums.persistent.model.registrar.PersistentPublicationInformation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicationInformationResourceHelper extends
    ResourceHelper<PublicationInformation, MutablePublicationInformation, Integer> {

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  PublicationInformationBuilder mPublicationInformationBuilder;

  @Autowired
  UserManager userManager;

  public JsonObject getPublicationInformation(final UriInfo pUriInfo) {
      String userId = userManager.get(
              SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
      List<PublicationInformation> pPublicationInformation = new ArrayList<>();
      try {
          pPublicationInformation =
                  mPublicationInformationManager.getEmployeePublicationInformation(userId);
      } catch (EmptyResultDataAccessException e) {

      }
      return toJson(pPublicationInformation, pUriInfo);
  }

  @Transactional
  public Response savePublicationInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    mPublicationInformationManager.deletePublicationInformation(userManager.get(
        SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());

    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray publicationJsonArray = entries.getJsonObject(0).getJsonArray("publication");
    int sizeOfPublicationJsonArray = publicationJsonArray.size();

    List<MutablePublicationInformation> mutablePublicationInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfPublicationJsonArray; i++) {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      mPublicationInformationBuilder.build(publicationInformation, publicationJsonArray.getJsonObject(i), localCache);
      mutablePublicationInformation.add(publicationInformation);
    }
    mPublicationInformationManager.savePublicationInformation(mutablePublicationInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<PublicationInformation> pPublicationInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(PublicationInformation publicationInformation : pPublicationInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, publicationInformation, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<PublicationInformation, MutablePublicationInformation, Integer> getContentManager() {
    return mPublicationInformationManager;
  }

  @Override
  protected Builder<PublicationInformation, MutablePublicationInformation> getBuilder() {
    return mPublicationInformationBuilder;
  }

  @Override
  protected String getETag(PublicationInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
