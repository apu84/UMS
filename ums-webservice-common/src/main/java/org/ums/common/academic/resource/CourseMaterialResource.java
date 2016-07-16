package org.ums.common.academic.resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.ums.common.Resource;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
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
          return mBinaryContentManager.move(actionItems(items, root),
              root + pJsonObject.getString("newPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL);
        case "createFolder":
          return mBinaryContentManager.createFolder(root + pJsonObject.getString("newPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL);
        case "remove":
          JsonArray deletedItems = pJsonObject.getJsonArray("items");
          return mBinaryContentManager.remove(actionItems(deletedItems, root),
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

  @GET
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Path("/download/semester/{semester-name}/course/{course-no}")
  public Response getBySemesterCourse(final @Context HttpServletRequest httpRequest,
                                      final @Context HttpServletResponse httpResponse,
                                      final @PathParam("semester-name") String pSemesterName,
                                      final @PathParam("course-no") String pCourseNo) throws Exception {
    String action = "", token = "", toFileName = "";
    List<String> downloadFiles = null;
    String root = "/" + pSemesterName + "/" + pCourseNo;
    action = httpRequest.getParameter("action");
    token = httpRequest.getParameter("token");

    if (!StringUtils.isEmpty(action) && !StringUtils.isEmpty(token)) {
      switch (action) {
        case "download":
          String filePath = httpRequest.getParameter("path");
          if (!StringUtils.isEmpty(filePath)) {
            Map<String, Object> response = mBinaryContentManager.download(root + filePath, token,
                BinaryContentManager.Domain.COURSE_MATERIAL);
            if (response != null) {
              InputStream fileStream = (InputStream) response.get("Content");
              for (String key : response.keySet()) {
                if (!key.equalsIgnoreCase("Content")) {
                  httpResponse.setHeader(key, response.get(key).toString());
                }
              }

              StreamUtils.copy(fileStream, httpResponse.getOutputStream());
              httpResponse.getOutputStream().flush();
            }
          } else {
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND);
            return responseBuilder.build();
          }
          break;
        case "downloadMultiple":
          downloadFiles = actionItems(httpRequest.getParameterValues("items[]"), root);
          toFileName = httpRequest.getParameter("toFilename");
          if (downloadFiles.size() > 0) {
            Map<String, Object> response = mBinaryContentManager.downloadAsZip(downloadFiles, toFileName, token,
                BinaryContentManager.Domain.COURSE_MATERIAL);
            if (response != null) {
              InputStream fileStream = (InputStream) response.get("Content");
              for (String key : response.keySet()) {
                if (!key.equalsIgnoreCase("Content")) {
                  httpResponse.setHeader(key, response.get(key).toString());
                }
              }

              StreamUtils.copy(fileStream, httpResponse.getOutputStream());
              httpResponse.getOutputStream().flush();
            }
          } else {
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND);
            return responseBuilder.build();
          }
          break;
      }

    }

    Response.ResponseBuilder responseBuilder = Response.ok();
    return responseBuilder.build();
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

  protected List<String> actionItems(final JsonArray pItems, final String pRoot) {
    List<String> actionItems = new ArrayList<>();
    for (int i = 0; i < pItems.size(); i++) {
      String actionItem = pItems.getString(i);
      actionItems.add(pRoot + actionItem);
    }

    return actionItems;
  }

  protected List<String> actionItems(final String[] pItems, final String pRoot) {
    List<String> actionItems = new ArrayList<>();
    for (int i = 0; i < pItems.length; i++) {
      String actionItem = pItems[i];
      actionItems.add(pRoot + actionItem);
    }

    return actionItems;
  }
}
