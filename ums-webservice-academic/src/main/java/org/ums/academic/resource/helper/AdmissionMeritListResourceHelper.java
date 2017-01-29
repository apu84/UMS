package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionMeritListBuilder;
import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionMeritListManager;
import org.ums.manager.FacultyManager;
import org.ums.manager.SemesterManager;
import org.ums.persistent.model.PersistentAdmissionMeritList;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Dec-16.
 */
@Component
public class AdmissionMeritListResourceHelper extends
    ResourceHelper<AdmissionMeritList, MutableAdmissionMeritList, Integer> {

  @Autowired
  private AdmissionMeritListManager mManager;

  @Autowired
  private AdmissionMeritListBuilder mBuilder;

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private FacultyManager mFacultyManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<MutableAdmissionMeritList> admissionMeritLists = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentAdmissionMeritList admissionMeritList = new PersistentAdmissionMeritList();
      getBuilder().build(admissionMeritList, jsonObject, localCache);
      admissionMeritLists.add(admissionMeritList);
    }

    getContentManager().create(admissionMeritLists);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public JsonObject getMeritList(final int pSemesterId, final int pFacultyId,
      int pAdmissionGroupId, final UriInfo pUriInfo) {
    List<AdmissionMeritList> admissionMeritLists =
        getContentManager().getMeritList(mSemesterManager.get(pSemesterId),
            mFacultyManager.get(pFacultyId), QuotaType.get(pAdmissionGroupId));

    return buildAdmissionMeritList(admissionMeritLists, pUriInfo);
  }

  private JsonObject buildAdmissionMeritList(final List<AdmissionMeritList> pMeritLists,
      final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(AdmissionMeritList readOnly : pMeritLists) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected AdmissionMeritListManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionMeritListBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AdmissionMeritList pReadonly) {
    return pReadonly.getLastModified();
  }
}
