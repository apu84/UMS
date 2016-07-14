package org.ums.common.academic.resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/academic/courseMaterial")
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseMaterialResource extends Resource {
  public static String DESTINATION = "destination";
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

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Path("/semester/{semester-name}/course/{course-no}/upload")
  public Object uploadBySemesterCourse(final @Context HttpServletRequest httpRequest,
                                       final @PathParam("semester-name") String pSemesterName,
                                       final @PathParam("course-no") String pCourseNo) throws Exception {


    String root = "/" + pSemesterName + "/" + pCourseNo;

    if (ServletFileUpload.isMultipartContent(httpRequest)) {
      return uploadFile(httpRequest, root);
    }
    return null;
  }

  private Object uploadFile(HttpServletRequest request, String pRootPath) throws Exception {
    String destination = null;
    Map<String, InputStream> files = new HashMap<>();

    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
    for (FileItem item : items) {
      if (item.isFormField()) {
        // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
        if (DESTINATION.equals(item.getFieldName())) {
          destination = item.getString();
        }
      } else {
        // Process form file field (input type="file").
        files.put(item.getName(), item.getInputStream());
      }
    }

    return mBinaryContentManager.upload(files,
        pRootPath + destination,
        BinaryContentManager.Domain.COURSE_MATERIAL);
  }
}
