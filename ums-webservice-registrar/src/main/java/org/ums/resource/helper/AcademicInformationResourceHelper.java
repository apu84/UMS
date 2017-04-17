package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AcademicInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.AcademicInformationManager;
import org.ums.persistent.model.registrar.PersistentAcademicInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AcademicInformationResourceHelper extends
    ResourceHelper<AcademicInformation, MutableAcademicInformation, Integer> {

  @Autowired
  AcademicInformationManager mAcademicInformationManager;

  @Autowired
  AcademicInformationBuilder mAcademicInformationBuilder;

  @Transactional
  public Response saveAcademicInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray academicJsonArray = entries.getJsonObject(0).getJsonArray("academic");
    int sizeOfAcademicJsonArray = academicJsonArray.size();

    List<MutableAcademicInformation> mutableAcademicInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfAcademicJsonArray; i++) {
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      mAcademicInformationBuilder.build(academicInformation, academicJsonArray.getJsonObject(i), localCache);
      mutableAcademicInformation.add(academicInformation);
    }
    mAcademicInformationManager.saveAcademicInformation(mutableAcademicInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AcademicInformation, MutableAcademicInformation, Integer> getContentManager() {
    return mAcademicInformationManager;
  }

  @Override
  protected Builder<AcademicInformation, MutableAcademicInformation> getBuilder() {
    return mAcademicInformationBuilder;
  }

  @Override
  protected String getETag(AcademicInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
