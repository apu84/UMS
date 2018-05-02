package org.ums.academic.resource.helper;

import com.itextpdf.text.DocumentException;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.RoutineResource;
import org.ums.builder.Builder;
import org.ums.builder.RoutineBuilder;
import org.ums.cache.LocalCache;
import org.ums.report.generator.ClassRoutineGenerator;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentRoutine;
import org.ums.persistent.model.PersistentSemester;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoutineResourceHelper extends ResourceHelper<Routine, MutableRoutine, Long> {

  private static final Logger mLogger = LoggerFactory.getLogger(RoutineResourceHelper.class);
  @Autowired
  private RoutineManager mManager;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private StudentManager mStudentManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  private ProgramManager mProgramManager;

  @Autowired
  private RoutineBuilder mBuilder;

  @Autowired
  private ClassRoutineGenerator mRoutineGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    /*MutableRoutine mutableRoutine = new PersistentRoutine();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableRoutine, pJsonObject, localCache);
    mutableRoutine.commit(false);*/
    List<PersistentRoutine> routines = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i=0;i<entries.size();i++){
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentRoutine routine = new PersistentRoutine();
      getBuilder().build(routine,jsonObject,localCache);
      routines.add(routine);
    }

    Map<String,List<PersistentRoutine>> statusWithRoutine = routines
        .stream()
        .collect(Collectors.groupingBy(Routine::getStatus));

    if(statusWithRoutine.get("created")!=null){
      List<PersistentRoutine> pRoutines = statusWithRoutine.get("created");
      List<MutableRoutine> mutableRoutines = new ArrayList<>();

      for(int i=0;i<pRoutines.size();i++){
        MutableRoutine mutableRoutine = pRoutines.get(i);
        mutableRoutines.add(mutableRoutine);
      }

      getContentManager().create(mutableRoutines);
    }

    if(statusWithRoutine.get("deleted")!=null){
      List<PersistentRoutine> pRoutines = statusWithRoutine.get("deleted");
      List<MutableRoutine> mutableRoutines = new ArrayList<>();

      for(int i=0;i<pRoutines.size();i++){
        MutableRoutine mutableRoutine = pRoutines.get(i);
        mutableRoutines.add(mutableRoutine);
      }
      getContentManager().delete(mutableRoutines);
    }

    if(statusWithRoutine.get("exist")!=null){
      List<PersistentRoutine> pRoutines = statusWithRoutine.get("exist");
      List<MutableRoutine> mutableRoutines = new ArrayList<>();

      for(int i=0;i<pRoutines.size();i++){
        MutableRoutine mutableRoutine = pRoutines.get(i);
        mutableRoutines.add(mutableRoutine);
      }

      getContentManager().update(mutableRoutines);
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response post(final int semesterId, final int programId, final int academicYear, final int academicSemester,
      JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableRoutine mutableRoutine = new PersistentRoutine();
    MutableSemester semester = new PersistentSemester();
    semester.setId(semesterId);
    mutableRoutine.setSemester(semester);
    MutableProgram program = new PersistentProgram();
    program.setId(programId);
    mutableRoutine.setProgram(program);
    mutableRoutine.setAcademicYear(academicYear);
    mutableRoutine.setAcademicSemester(academicSemester);
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableRoutine, pJsonObject, localCache);

    mutableRoutine.create();
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(RoutineResource.class).path(RoutineResource.class, "get")
            .build(mutableRoutine.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject buildRoutines(final List<Routine> pRoutines, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Routine readOnly : pRoutines) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRoutineForTeacher(final UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    // Employee employee = mEmployeeManager.getByEmployeeId(employeeId);
    List<Routine> routines = getContentManager().getTeacherRoutine(employeeId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Routine routine : routines) {
      children.add(toJson(routine, pUriInfo, localCache));
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public void getRoomBasedRoutineReport(final OutputStream pOutputStream, final int pSemesterId, final int pRoomId)
      throws DocumentException, IOException {
    mRoutineGenerator.createRoomBasedClassRoutineReport(pOutputStream, pSemesterId, pRoomId);
  }

  public void getRoutineReportForTeacher(final OutputStream pOutputStream) throws DocumentException, IOException {
    mRoutineGenerator.createClassRoutineTeacherReport(pOutputStream);
  }

  public JsonObject getRoutineForStudent() {
    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(mStudentId);
    List<Routine> routines = new ArrayList<>();

    try {
      routines = getContentManager()
          .getStudentRoutine(student)
          .stream()
          .filter((r)->r.getSection().equals(student.getTheorySection() )|| r.getSection().equals(student.getSessionalSection()))
          .collect(Collectors.toList());
    } catch(Exception e) {
      mLogger.error(e.getMessage());
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Routine routine : routines) {
      children.add(toJson(routine, null, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRoutineForEmployee(final int semesterId,
                                          final int academicYear,
                                          final int academicSemester,
                                          final String section,
                                          final UriInfo pUriInfo) {
    List<Routine> routines = new ArrayList<>();
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.get(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> programLIst = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram -> pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());
    //DateFormat timeFormat = new SimpleDateFormat("hh:mm a");



    try{

      routines = getContentManager().
          getEmployeeRoutine(semesterId, programLIst.get(0).getId(), academicYear, academicSemester).stream()
          .filter(routine-> routine.getSection().charAt(0)== section.charAt(0))
          .sorted(Comparator.comparing(Routine::getDay).thenComparing( r->r.getStartTime().substring(Math.max(r.getStartTime().length()-2,0))).thenComparing(Routine::getStartTime))
          .collect(Collectors.toList());

    }catch (EmptyResultDataAccessException e){

      //// TODO: 17-Sep-16 : check for catching proper exception

      mLogger.error(e.getMessage());
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for (Routine routine : routines) {
      children.add(toJson(routine, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public RoutineManager getContentManager() {
    return mManager;
  }

  @Override
  public Builder<Routine, MutableRoutine> getBuilder() {
    return mBuilder;
  }

  @Override
  public String getETag(Routine pReadOnly) {
    return pReadOnly.getLastModified();
  }
}
