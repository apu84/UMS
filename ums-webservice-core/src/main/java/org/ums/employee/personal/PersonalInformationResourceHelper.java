package org.ums.employee.personal;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.SimilarityScore;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PersonalInformationResourceHelper extends
    ResourceHelper<PersonalInformation, MutablePersonalInformation, String> {

  @Autowired
  private PersonalInformationManager mManager;

  @Autowired
  private PersonalInformationBuilder mBuilder;

  public JsonObject getPersonalInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    PersonalInformation personalInformation = new PersistentPersonalInformation();
    try {
      /* personalInformation = mManager.getPersonalInformation(pEmployeeId); */
      personalInformation = mManager.get(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }
    return buildJson(personalInformation, pUriInfo, localCache);
  }

  public JsonObject getSimilarUsers(final String pFirstName, final String pLastName, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    List<Map<PersonalInformation, Double>> similarUsers = new ArrayList<Map<PersonalInformation, Double>>();
    String fullName = (pFirstName.concat(" " + pLastName)).trim();
    List<PersonalInformation> personalInformationList = mManager.getAll();
    for(PersonalInformation personalInformation : personalInformationList) {
      JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
      double score = jaccardSimilarity.apply(fullName, personalInformation.getFullName());
      if(score > 0.65) {
        Map<PersonalInformation, Double> map = new HashMap<>();
        map.put(personalInformation, score * 100);
        similarUsers.add(map);
      }
    }
    return buildJson(similarUsers, pUriInfo);
  }

  public Response updatePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    LocalCache localeCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mBuilder.build(personalInformation, jsonObject, localeCache);
    mManager.update(personalInformation);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response savePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    LocalCache localeCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mBuilder.build(personalInformation, jsonObject, localeCache);
    mManager.create(personalInformation);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  private JsonObject buildJson(PersonalInformation pPersonalInformation, UriInfo pUriInfo, LocalCache localCache) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    if(pPersonalInformation.getId() != null) {
      children.add(toJson(pPersonalInformation, pUriInfo, localCache));
    }
    jsonObject.add("entries", children);
    localCache.invalidate();
    return jsonObject.build();
  }

  private JsonObject buildJson(List<Map<PersonalInformation, Double>> similarUsers, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Map<PersonalInformation, Double> personalInformation : similarUsers) {
      for(Map.Entry<PersonalInformation, Double> entry : personalInformation.entrySet()) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        mBuilder.build(jsonObjectBuilder, entry.getKey(), pUriInfo, localCache);
        jsonObjectBuilder.add("score", entry.getValue());
        children.add(jsonObjectBuilder.build());
      }
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @Override
  protected PersonalInformationManager getContentManager() {
    return mManager;
  }

  @Override
  protected PersonalInformationBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(PersonalInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
