package org.ums.common.academic.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.ums.manager.BinaryContentManager;
import org.ums.resource.Resource;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractCourseMaterialResource extends Resource {
  public static String DESTINATION = "destination";
  private DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @POST
  @Produces(Resource.MIME_TYPE_JSON)
  @Path("/semester/{semester-name}/course/{course-no}")
  public Object getBySemesterCourse(final @Context Request pRequest,
                                    final @PathParam("semester-name") String pSemesterName,
                                    final @PathParam("course-no") String pCourseNo,
                                    final JsonObject pJsonObject) throws Exception {
    Map<String, Object> result = new HashMap<>();
    if (pJsonObject != null
        && pJsonObject.containsKey("action")) {
      switch (pJsonObject.getString("action")) {

        case "list":
          Map<String, String> additionalListParams = buildAdditionalParams(pJsonObject);
          result.put("result", getBinaryContentManager().list(pJsonObject.getString("path"),
              additionalListParams,
              BinaryContentManager.Domain.COURSE_MATERIAL, pSemesterName, pCourseNo));
          break;

        case "rename":
          result.put("result", getBinaryContentManager().rename(pJsonObject.getString("item"),
              pJsonObject.getString("newItemPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;

        case "move":
          JsonArray items = pJsonObject.getJsonArray("items");
          result.put("result", getBinaryContentManager().move(actionItems(items),
              pJsonObject.getString("newPath"),
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;

        case "createFolder":
          Map<String, String> additionalParams = buildAdditionalParams(pJsonObject);
          result.put("result", getBinaryContentManager().createFolder(pJsonObject.getString("newPath"),
              additionalParams,
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;

        case "createAssignmentFolder":
          Map<String, String> additionalAssignmentParams = buildAdditionalParams(pJsonObject);
          result.put("result", getBinaryContentManager().createAssignmentFolder(pJsonObject.getString("newPath"),
              mDateFormat.parse(pJsonObject.getString("startDate")),
              mDateFormat.parse(pJsonObject.getString("endDate")),
              additionalAssignmentParams,
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName, pCourseNo));
          break;

        case "remove":
          JsonArray deletedItems = pJsonObject.getJsonArray("items");
          result.put("result", getBinaryContentManager().remove(actionItems(deletedItems),
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;

        case "copy":
          JsonArray copiedItems = pJsonObject.getJsonArray("items");
          List<String> copiedFiles = actionItems(copiedItems);
          String newPath = pJsonObject.getString("newPath");
          String singleFile = pJsonObject.containsKey("singleFileName") ? pJsonObject.getString("singleFilename") : "";

          result.put("result", getBinaryContentManager().copy(copiedFiles,
              newPath, singleFile,
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;
        case "compress":
          JsonArray compressItems = pJsonObject.getJsonArray("items");
          List<String> compressFiles = actionItems(compressItems);
          String destination = pJsonObject.getString("destination");
          String compressedFileName = pJsonObject.getString("compressedFilename");
          result.put("result", getBinaryContentManager().compress(compressFiles,
              destination, compressedFileName,
              BinaryContentManager.Domain.COURSE_MATERIAL,
              pSemesterName,
              pCourseNo));
          break;
      }
    }

    return result;
  }

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Path("/semester/{semester-name}/course/{course-no}/upload")
  public Object uploadBySemesterCourse(final @Context HttpServletRequest httpRequest,
                                       final @PathParam("semester-name") String pSemesterName,
                                       final @PathParam("course-no") String pCourseNo) throws Exception {
    Map<String, Object> result = new HashMap<>();
    if (ServletFileUpload.isMultipartContent(httpRequest)) {
      result.put("result", uploadFile(httpRequest,
          pSemesterName,
          pCourseNo));
      return result;
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
    action = httpRequest.getParameter("action");
    token = httpRequest.getParameter("token");

    if (!StringUtils.isEmpty(action) && !StringUtils.isEmpty(token)) {
      switch (action) {
        case "download":
          String filePath = httpRequest.getParameter("path");
          if (!StringUtils.isEmpty(filePath)) {
            Map<String, Object> response = getBinaryContentManager().download(filePath, token,
                BinaryContentManager.Domain.COURSE_MATERIAL, pSemesterName, pCourseNo);
            if (response != null) {
              writeToResponse(response, httpResponse);
            }
          } else {
            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND);
            return responseBuilder.build();
          }
          break;

        case "downloadMultiple":
          downloadFiles = actionItems(httpRequest.getParameterValues("items[]"));
          toFileName = httpRequest.getParameter("toFilename");
          if (downloadFiles.size() > 0) {
            Map<String, Object> response = getBinaryContentManager().downloadAsZip(downloadFiles, toFileName, token,
                BinaryContentManager.Domain.COURSE_MATERIAL,
                pSemesterName,
                pCourseNo);
            if (response != null) {
              writeToResponse(response, httpResponse);
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

  protected void writeToResponse(final Map<String, Object> pResponse,
                                 final HttpServletResponse pHttpServletResponse) throws Exception {
    InputStream fileStream = (InputStream) pResponse.get("Content");
    for (String key : pResponse.keySet()) {
      if (!key.equalsIgnoreCase("Content")) {
        pHttpServletResponse.setHeader(key, pResponse.get(key).toString());
      }
    }

    StreamUtils.copy(fileStream, pHttpServletResponse.getOutputStream());
    pHttpServletResponse.getOutputStream().flush();
    IOUtils.closeQuietly(fileStream);
  }

  private Object uploadFile(HttpServletRequest request, String... pRootPath) throws Exception {
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

    return getBinaryContentManager().upload(files,
        destination,
        BinaryContentManager.Domain.COURSE_MATERIAL,
        pRootPath);
  }

  protected List<String> actionItems(final JsonArray pItems) {
    List<String> actionItems = new ArrayList<>();
    for (int i = 0; i < pItems.size(); i++) {
      String actionItem = pItems.getString(i);
      actionItems.add(actionItem);
    }

    return actionItems;
  }

  protected List<String> actionItems(final String[] pItems) {
    List<String> actionItems = new ArrayList<>();
    for (int i = 0; i < pItems.length; i++) {
      String actionItem = pItems[i];
      actionItems.add(actionItem);
    }
    return actionItems;
  }

  private Map<String, String> buildAdditionalParams(JsonObject pJsonObject) throws Exception {
    if (pJsonObject.containsKey("additionalParams")) {
      pJsonObject.getJsonObject("additionalParams");
      return new ObjectMapper().readValue(pJsonObject.getJsonObject("additionalParams").toString(), HashMap.class);
    }
    return null;
  }

  protected abstract BinaryContentManager<byte[]> getBinaryContentManager();
}
