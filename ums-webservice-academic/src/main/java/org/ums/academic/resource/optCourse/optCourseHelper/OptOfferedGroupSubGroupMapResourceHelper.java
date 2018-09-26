package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupSubGroupMapBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptOfferedGroupManager;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupSubGroupMap;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupSubGroupMapResourceHelper extends
    ResourceHelper<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long> {
  @Autowired
  OptOfferedGroupSubGroupMapManager mManager;
  @Autowired
  OptOfferedGroupSubGroupMapBuilder mBuilder;
  @Autowired
  OptOfferedGroupManager mOptOfferedGroupManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getGroupInfo(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester,
      final UriInfo pUriInfo) {
    List<OptOfferedGroupSubGroupMap> optOfferedGroupSubGroupMap =
        mManager.getBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
    List<MutableOptOfferedGroupSubGroupMap> list = new ArrayList<>();
    for(OptOfferedGroupSubGroupMap app : optOfferedGroupSubGroupMap) {
      MutableOptOfferedGroupSubGroupMap info = new PersistentOptOfferedGroupSubGroupMap();
      info.setGroupId(app.getGroupId());
      info.setGroupName(mOptOfferedGroupManager.get(app.getGroupId()).getGroupName());
      info.setSubGroupId(app.getSubGroupId());
      info.setSubGroupName(app.getSubGroupName());
      list.add(info);
    }

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableOptOfferedGroupSubGroupMap app : list) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected ContentManager<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptOfferedGroupSubGroupMap pReadonly) {
    return null;
  }
}
