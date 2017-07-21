package org.ums.resource.leavemanagement;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.ApplicationType;
import org.ums.enums.common.*;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.AttachmentManager;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.manager.common.LmsTypeManager;
import org.ums.persistent.model.common.PersistentAttachment;
import org.ums.persistent.model.common.PersistentLmsAppStatus;
import org.ums.persistent.model.common.PersistentLmsApplication;
import org.ums.resource.ResourceHelper;
import org.ums.services.leave.management.LeaveManagementService;
import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 08-May-17.
 */
@Component
public class LmsApplicationResourceHelper extends ResourceHelper<LmsApplication, MutableLmsApplication, Long> {

  @Autowired
  private LmsApplicationManager mManager;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private LmsApplicationBuilder mBuilder;

  @Autowired
  private LmsAppStatusManager mLmsAppStatusManager;

  @Autowired
  private LmsTypeManager mLmsTypeManager;

  @Autowired
  private LeaveManagementService mLeaveManagementService;

  @Autowired
  private AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  private AttachmentManager mAttachmentManager;

  ApplicationContext applicationContext = AppContext.getApplicationContext();

  MessageChannel lmsChannel = applicationContext.getBean("lmsChannel", MessageChannel.class);

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject saveApplication(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentLmsApplication application = new PersistentLmsApplication();
    getBuilder().build(application, jsonObject, localCache);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    Long appId = new Long(1L);
    String ouputMessage = checkApplicationType(application);
    if(ouputMessage.equals("")) {
      appId = inserIntoLeaveApplicationStatus(application);
      jsonObjectBuilder.add("id", appId.toString());
      jsonObjectBuilder.add("message", ouputMessage);
    }
    else {
      jsonObjectBuilder.add("message", ouputMessage);
    }

    children.add(jsonObjectBuilder);
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private String checkApplicationType(PersistentLmsApplication application) {
    String outputMessage = "";
    if(application.getLmsType().getId() == LeaveCategories.STUDY_LEAVE_ON_WITHOUT_PAY.getId()) {
      LocalDate fromDate = application.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate toDate = application.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      Period period = Period.between(fromDate, toDate);
      int year = period.getYears();
      int month = period.getMonths();
      int day = period.getDays();
      if(period.getYears() >= 1 && month >= 1)
        return outputMessage = "Study leave must be renewed every one year";

    }
    return outputMessage;
  }

  public Response uploadFile(final File pInputStream, final String id, final String name, final UriInfo pUriInfo)
      throws IOException {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String serverFilename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + name;
    serverFilename.replaceAll("-", "");
    serverFilename.replaceAll(":", "");
    File newFile = new File(pInputStream.getParent(), serverFilename);
    Files.move(pInputStream.toPath(), newFile.toPath());

    Message<File> messageA = MessageBuilder.withPayload(newFile).build();
    lmsChannel.send(messageA);

    PersistentAttachment attachment = new PersistentAttachment();
    attachment.setApplicationType(ApplicationType.LEAVE);
    attachment.setApplicationId(id);
    attachment.setFileName(name);
    attachment.setServerFileName("files/lms/" + serverFilename);
    mAttachmentManager.create(attachment);
    // FileUtils.cleanDirectory(new File("H:/Apache/apache-tomcat-7.0.47/temp"));

    pInputStream.deleteOnExit();
    newFile.deleteOnExit();
    System.gc();
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private Long inserIntoLeaveApplicationStatus(PersistentLmsApplication pApplication) {
    Long pAppId = new Long(0);
    MutableLmsAppStatus lmsAppStatus = new PersistentLmsAppStatus();
    lmsAppStatus.setLmsApplicationId(pAppId);
    lmsAppStatus.setActionTakenOn(pApplication.getAppliedOn());
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Employee employee = mEmployeeManager.get(user.getEmployeeId());
    lmsAppStatus.setActionTakenById(user.getEmployeeId());
    lmsAppStatus.setComments(LeaveApplicationStatus.APPLIED.getLabel());
    List<AdditionalRolePermissions> rolePermissionsStream = mAdditionalRolePermissionsManager.getAdditionalRole(employee.getDepartment().getId()).stream().filter(r -> r.getRoleId() == RoleType.DEPT_HEAD.getId()).collect(Collectors.toList());

    // todo add more roles, currently mst_role table in db is not complete.
    List<Integer> roles = user.getRoleIds();
    String message = "Leave Application from employee: " + employee.getEmployeeName() + " of department: "
        + employee.getDepartment().getShortName() + " is waiting for your approval.";
    if (rolePermissionsStream.get(0).getUserId().equals(user.getId())
        || user.getPrimaryRole().getId() == RoleType.COE.getId()
        || user.getPrimaryRole().getId() == RoleType.REGISTRAR.getId()
        || user.getPrimaryRole().getId() == RoleType.LIBRARIAN.getId()) {
      lmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.WAITING_FOR_VC_APPROVAL);
      pApplication.setLeaveApplicationStatus(LeaveApplicationApprovalStatus.WAITING_FOR_VC_APPROVAL);
      mLeaveManagementService.setNotification("vc", message);
    } else {
      lmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.WAITING_FOR_HEAD_APPROVAL);
      pApplication.setLeaveApplicationStatus(LeaveApplicationApprovalStatus.WAITING_FOR_HEAD_APPROVAL);

      mLeaveManagementService.setNotification(rolePermissionsStream.get(0).getUserId(), message);
    }

    pAppId = getContentManager().create(pApplication);
    lmsAppStatus.setLmsApplicationId(pAppId);

    mLmsAppStatusManager.create(lmsAppStatus);
    return pAppId;
  }

  public JsonObject getPendingLeavesOfEmployee(UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsApplication> applications = getContentManager().getPendingLmsApplication(user.getEmployeeId());
    return getJsonObject(pUriInfo, applications);
  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<LmsApplication> pApplications) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(LmsApplication application : pApplications) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, application, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRemainingLeaves() {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    String employeeId = mUserManager.get(userId).getEmployeeId();
    return getRemainingLeavesJsonObject(employeeId);
  }

  public JsonObject getRemainingLeaves(String pEmployeeId) {
    return getRemainingLeavesJsonObject(pEmployeeId);
  }

  private JsonObject getRemainingLeavesJsonObject(String pEmployeeId) {
    List<LmsType> lmsTypes = getLeaveTypes();
    int year = Calendar.getInstance().get(Calendar.YEAR);
    List<LmsApplication> applications = getContentManager()
        .getLmsApplication(pEmployeeId, year);
    Map<Integer, List<LmsApplication>> applicationMap = applications
        .stream()
        .filter(app -> app.getApplicationStatus() == LeaveApplicationApprovalStatus.APPLICATION_APPROVED)
        .collect(Collectors.groupingBy(LmsApplication::getLeaveTypeId));

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for (LmsType lmsType : lmsTypes) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      jsonObject.add("leaveTypeId", lmsType.getId());
      jsonObject.add("leaveName", lmsType.getName());
      int leavesTaken = getLeavesTaken(applicationMap, lmsType);

      jsonObject.add("daysLeft", applicationMap.get(lmsType.getId()) != null ? getDateOutputModifiedFormat(lmsType.getMaxDuration() - leavesTaken) : getDateOutputModifiedFormat(lmsType.getMaxDuration()) + "");
        jsonObject.add("daysLeftNumber", applicationMap.get(lmsType.getId()) != null ? lmsType.getMaxDuration() - leavesTaken : lmsType.getMaxDuration());

        children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private String getDateOutputModifiedFormat(int duration) {
    int periodNumber = duration;

    Period period = Period.ofDays(duration);
    String days = "";
    if(periodNumber > (365)) {
      int year = periodNumber / 365;
      int month = (periodNumber % 365) / 30;
      int day = ((periodNumber % 365) % 30);
      days = year + " year/s, " + month + " month/s," + day + " day/s";
    }
    else if(periodNumber > 30) {
      int month = periodNumber / 30;
      int day = (periodNumber % 30);
      days = month + " month/s," + day + " day/s";
    }
    else {
      days = period.getDays() + " day/s";
    }
    return days;
  }

  private int getLeavesTaken(Map<Integer, List<LmsApplication>> pApplicationMap, LmsType lmsType) {
    int leavesTaken = 0;
    if(pApplicationMap.get(lmsType.getId()) != null)
      for(LmsApplication application : pApplicationMap.get(lmsType.getId())) {
        leavesTaken +=
            (application.getToDate().getTime() - application.getFromDate().getTime()) / (1000 * 60 * 60 * 24);
      }
    return leavesTaken;
  }

  protected JsonObject getApprovedLeaveApplicationsWithinDateRange(String pStartDate, String pEndDate, UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    List<LmsApplication> leaveApplications =
        getContentManager().getApprovedApplicationsWithinDateRange(user.getEmployeeId(), pStartDate, pEndDate);
    return getJsonObject(pUriInfo, leaveApplications);
  }

  private List<LmsType> getLeaveTypes() {
    List<LmsType> lmsTypes = new ArrayList<>();
    Employee employee =
        mEmployeeManager.get(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(employee.getEmploymentType().equals(EmployeeType.TEACHER.getId() + "")) {
      if(employee.getGender().equals("M"))
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.MALE);
      else
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.FEMALE);
    }
    else {
      if(employee.getGender().equals("M"))
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.MALE);
      else
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.FEMALE);
    }
    return lmsTypes;
  }

  @Override
  protected LmsApplicationManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<LmsApplication, MutableLmsApplication> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(LmsApplication pReadonly) {
    return pReadonly.getLastModified();
  }
}
