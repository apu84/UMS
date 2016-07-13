package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/academic/courseMaterial")
@Consumes(Resource.MIME_TYPE_JSON)

public class CourseMaterialResource extends Resource {

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  CourseManager mCourseManager;

  @POST
  @Produces(Resource.MIME_TYPE_JSON)
  @Path("/semester/{semester-name}/course/{course-no}")
  public Object getBySemesterCourse(final @Context Request pRequest,
                                                       final @PathParam("semester-name") String pSemesterName,
                                                       final @PathParam("course-no") String pCourseNo,
                                                       final JsonObject pJsonObject) throws Exception {
    String root = "/" + pSemesterName + "/" + pCourseNo;
    Map<String, Object> result = new HashMap<>();
    if (pJsonObject != null
        && pJsonObject.containsKey("action")) {
      switch (pJsonObject.getString("action")) {
        case "list":
          result.put("result", mBinaryContentManager.list(root + pJsonObject.getString("path"),
              BinaryContentManager.Domain.COURSE_MATERIAL));
          return result;
        case "rename":
          return mBinaryContentManager.rename(root + pJsonObject.getString("item"),
              root + pJsonObject.getString("newItemPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL);
        case "move":
          JsonArray items = pJsonObject.getJsonArray("items");
          List<String> moveItems = new ArrayList<>();
          for (int i = 0; i < items.size(); i++) {
            String moveItem = items.getString(i);
            moveItems.add(root + moveItem);
          }
          return mBinaryContentManager.move(moveItems,
              root + pJsonObject.getString("newPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL);
        case "createFolder":
          return mBinaryContentManager.createFolder(root + pJsonObject.getString("newPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL);

      }
    }

    return null;
  }
}
