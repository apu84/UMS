package org.ums.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionStudentBuilder;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.enums.DepartmentSelectionType;
import org.ums.report.generator.AdmissionStudentGenerator;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.manager.FacultyManager;
import org.ums.persistent.model.PersistentAdmissionStudent;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */
@Component
public class AdmissionStudentResourceHelper extends
    ResourceHelper<AdmissionStudent, MutableAdmissionStudent, String> {

  private static final Logger mLogger = LoggerFactory
      .getLogger(AdmissionStudentResourceHelper.class);

  @Autowired
  AdmissionStudentManager mManager;

  @Autowired
  AdmissionStudentBuilder mBuilder;

  @Autowired
  FacultyManager mFacultyManager;

  @Autowired
  AdmissionTotalSeatManager mAdmissionTotalSeatManager;

  @Autowired
  AdmissionStudentGenerator mGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Transactional
  public Response postTaletalkData(JsonObject pJsonObject, final int pSemesterId,
      final ProgramType pProgramType, UriInfo pUriInfo) throws Exception {

    int dataSize = getContentManager().getDataSize(pSemesterId, pProgramType);
    if(dataSize == 0) {
      List<MutableAdmissionStudent> students = new ArrayList<>();
      JsonArray entries = pJsonObject.getJsonArray("entries");

      for(int i = 0; i < entries.size(); i++) {
        LocalCache localCache = new LocalCache();
        JsonObject jsonObject = entries.getJsonObject(i);
        PersistentAdmissionStudent student = new PersistentAdmissionStudent();
        getBuilder().build(student, jsonObject, localCache);
        students.add(student);
      }

      getContentManager().saveTaletalkData(students);
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response saveMeritListData(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<MutableAdmissionStudent> students = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentAdmissionStudent student = new PersistentAdmissionStudent();
      getBuilder().build(student, jsonObject, "meritList", localCache);
      students.add(student);
    }
    getContentManager().saveMeritList(students);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();

  }

  // kawsurilu

  // @Transactional
  // public Response putVerificationStatus(JsonObject pJsonObject, UriInfo pUriInfo) {
  // MutableAdmissionStudent student = new PersistentAdmissionStudent();
  // JsonArray entries = pJsonObject.getJsonArray("entries");
  //
  // LocalCache localCache = new LocalCache();
  // JsonObject jsonObject = entries.getJsonObject(0);
  // getBuilder().setVerificationStatus(student, jsonObject, localCache);
  //
  // getContentManager().setVerificationStatus(student);
  // URI contextURI = null;
  // Response.ResponseBuilder builder = Response.created(contextURI);
  // builder.status(Response.Status.CREATED);
  // return builder.build();
  // }

  //

  @Transactional
  public JsonObject saveDepartmentSelectionInfoAndRetrieveNextStudent(JsonObject pJsonObject,
      final DepartmentSelectionType pDepartmentSelectionType, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentAdmissionStudent student = new PersistentAdmissionStudent();
    getBuilder().build(student, jsonObject, pDepartmentSelectionType, localCache);
    getContentManager().updateDepartmentSelection(student, pDepartmentSelectionType);
    AdmissionStudent nextStudent =
        getContentManager().getNextStudentForDepartmentSelection(student.getSemester().getId(),
            student.getProgramType(), student.getUnit(), student.getQuota());

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonObjectBuilder jsonObjectForNext = Json.createObjectBuilder();
    getBuilder().admissionStudentBuilder(jsonObjectForNext, nextStudent, pUriInfo, localCache,
        "meritList");
    children.add(jsonObjectForNext);
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getTaletalkData(final int pSemesterId, final ProgramType pProgramType,
      final UriInfo pUriInfo) {
    List<AdmissionStudent> students;
    try {
      students = getContentManager().getTaletalkData(pSemesterId, pProgramType);
    } catch(EmptyResultDataAccessException e) {
      mLogger.error(e.getMessage());
      students = new ArrayList<>(); // just for skipping while we have no data in the db.
    }

    return jsonCreator(students, "taletalkData", pUriInfo);
  }

  public JsonObject getCurrentDepartmentAllocationStatistics(final int pSemesterId,
                                                             final ProgramType pProgramType,
                                                             final QuotaType pQuotaType,
                                                             String pUnit,
                                                             final UriInfo pUriInfo){

    List<AdmissionTotalSeat> totalSeats = mAdmissionTotalSeatManager.getAdmissionTotalSeat(pSemesterId, pProgramType, pQuotaType);
    List<AdmissionStudent> students = getContentManager().getMeritList(pSemesterId,pQuotaType, pUnit,pProgramType);
    Map<Integer, List<AdmissionStudent>> allocatedProgramMapStudents = students
        .parallelStream()
        .collect(Collectors.groupingBy(AdmissionStudent::getProgramIdByMerit));
    Map<Integer, List<AdmissionStudent>> waitingProgramMapStudents = students
        .parallelStream()
        .collect(Collectors.groupingBy(AdmissionStudent::getProgramIdByTransfer));

    return getStatisticsJsonObject(totalSeats, allocatedProgramMapStudents, waitingProgramMapStudents);
  }

  private JsonObject getStatisticsJsonObject(List<AdmissionTotalSeat> pTotalSeats,
      Map<Integer, List<AdmissionStudent>> pAllocatedProgramMapStudents,
      Map<Integer, List<AdmissionStudent>> pWaitingProgramMapStudents) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionTotalSeat seat : pTotalSeats) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      jsonObject.add("programId", seat.getProgram().getId());
      jsonObject.add("programName", seat.getProgram().getShortName().replaceAll("BSc in ", ""));
      jsonObject.add("allocatedSeat", seat.getTotalSeat());
      int selected = 0;
      int waiting = 0;

      if(pAllocatedProgramMapStudents.get(seat.getProgram().getId()) != null) {
        selected = pAllocatedProgramMapStudents.get(seat.getProgram().getId()).size();
      }
      if(pWaitingProgramMapStudents.get(seat.getProgram().getId()) != null) {
        waiting = pWaitingProgramMapStudents.get(seat.getProgram().getId()).size();
      }
      jsonObject.add("selected", selected);
      jsonObject.add("remaining", (seat.getTotalSeat() - selected));
      jsonObject.add("waiting", waiting);
      children.add(jsonObject);
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getAdmissionStudentByReceiptId(final int pSemesterId,
      final ProgramType pProgramType, final String pReceiptId, final UriInfo pUriInfo) {

    AdmissionStudent student =
        getContentManager().getAdmissionStudent(pSemesterId, pProgramType, pReceiptId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    getBuilder().admissionStudentBuilder(jsonObject, student, pUriInfo, localCache, "meritList");
    children.add(jsonObject);
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  // kawsurilu
  // don't need meritType

  public JsonObject getCandidatesList(final ProgramType pProgramType, final int pSemesterId,
      final UriInfo pUriInfo) {
    System.out.println();
    List<AdmissionStudent> student =
        getContentManager().getAllCandidates(pProgramType, pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionStudent admissionStudent : student) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().getAdmissionStudentBuilder(jsonObject, admissionStudent, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  //

  public JsonObject getAdmissionMeritList(final int pSemesterId, final ProgramType pProgramType,
      final QuotaType pQuotaType, String pUnit, final UriInfo pUriInfo) {
    List<AdmissionStudent> students =
        getContentManager().getMeritList(pSemesterId, pQuotaType, pUnit, pProgramType);
    return jsonCreator(students, "meritList", pUriInfo);
  }

  public JsonObject getTaletalkData(final int pSemesterId, final ProgramType pProgramType,
      final QuotaType pQuotaType, final String pUnit, final UriInfo pUriInfo) {
    List<AdmissionStudent> students =
        getContentManager().getTaletalkData(pSemesterId, pQuotaType, pUnit, pProgramType);
    return jsonCreator(students, "taletalkData", pUriInfo);
  }

  public List<AdmissionStudent> getTaletalkData(final int pSemesterId,
      final ProgramType pProgramType) {
    List<AdmissionStudent> students = new ArrayList<>();
    // students = getContentManager().getTaletalkData(pSemesterId, pProgramType);
    try {
      students = getContentManager().getTaletalkData(pSemesterId, pProgramType);
    } catch(EmptyResultDataAccessException e) {
      mLogger.error(e.getMessage());
      students = new ArrayList<>(); // just for skipping while we have no data in the db.
    }

    return students;
  }

  public void getTaletalkDataXlesFormat(final OutputStream pOutputStream, int pSemesterId)
      throws Exception {
    mGenerator.createABlankTaletalkDataFormatFile(pOutputStream, pSemesterId);
  }

  public void getMeritLisXlesFormat(final OutputStream pOutputStream, int pSemesterId)
      throws Exception {
    mGenerator.createABlankMeritListUploadFormatFile(pOutputStream, pSemesterId);
  }

  private JsonObject jsonCreator(List<AdmissionStudent> pStudentLIst, String pType, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionStudent student : pStudentLIst) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().admissionStudentBuilder(jsonObject, student, pUriInfo, localCache, pType);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected AdmissionStudentManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionStudentBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
