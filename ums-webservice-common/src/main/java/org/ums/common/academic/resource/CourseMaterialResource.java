package org.ums.common.academic.resource;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Path("/semester/{semester-name}/course/{course-no}/upload")
  public Object uploadBySemesterCourse(final @Context Request pRequest,
                                       final @PathParam("semester-name") String pSemesterName,
                                       final @PathParam("course-no") String pCourseNo,
                                       @FormDataParam("files") List<FormDataBodyPart> bodyParts,
                                       @FormDataParam("files") FormDataContentDisposition fileDispositions) throws Exception {
    String root = "/" + pSemesterName + "/" + pCourseNo;
    for (int i = 0; i < bodyParts.size(); i++) {
      /*
       * Casting FormDataBodyPart to BodyPartEntity, which can give us
			 * InputStream for uploaded file
			 */
      BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyParts.get(i).getEntity();
      String name = bodyParts.get(i).getName();
      String destination = root;
      if (name.equalsIgnoreCase("destination")) {
        destination = destination + bodyParts.get(i).getValue();
      }
      String fileName = bodyParts.get(i).getContentDisposition().getFileName();
      saveFile(bodyPartEntity.getInputStream(), destination + "/" + fileName);
    }
    return null;
  }

  private void saveFile(InputStream file, String name) {
    try {
      /* Change directory path */
      java.nio.file.Path path = FileSystems.getDefault().getPath("/Volumes/Drive2/temp/file/" + name);
      /* Save InputStream as file */
      Files.copy(file, path);
    } catch (IOException ie) {
      ie.printStackTrace();
    }
  }
}
