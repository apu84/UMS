package org.ums.resource.helper.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.employee.*;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.Employee.AcademicInformation;
import org.ums.domain.model.immutable.registrar.Employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.Employee.*;
import org.ums.manager.registrar.Employee.*;
import org.ums.persistent.model.registrar.Employee.*;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class EmployeeInformationResourceHelper extends
    ResourceHelper<EmployeeInformation, MutableEmployeeInformation, Integer> {

  private static final Logger mLoger = LoggerFactory.getLogger(EmployeeInformationResourceHelper.class);

  @Autowired
  AcademicInformationManager mAcademicInformationManager;

  @Autowired
  AwardInformationManager mAwardInformationManager;

  @Autowired
  EmployeeInformationManager mEmployeeInformationManager;

  @Autowired
  ExperienceInformationManager mExperienceInformationManager;

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  TrainingInformationManager mTrainingInformationManager;

  @Autowired
  AcademicInformationBuilder mAcademicInformationBuilder;

  @Autowired
  AwardInformationBuilder mAwardInformationBuilder;

  @Autowired
  EmployeeInformationBuilder mEmployeeInformationBuilder;

  @Autowired
  ExperienceInformationBuilder mExperienceInformationBuilder;

  @Autowired
  PersonalInformationBuilder mPersonalInformationBuilder;

  @Autowired
  PublicationInformationBuilder mPublicationInformationBuilder;

  @Autowired
  TrainingInformationBuilder mTrainingInformationBuilder;

  public JsonObject getEmployeeAcademicInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeeAwardInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeeInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeeExperienceInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeePersonalInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeePublicationInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeeTrainingInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  @Transactional
  public Response saveEmployeeInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    System.out.println("I am in saveEmployeeInformation(). And it is a helper Helper");

    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject JsonObject = entries.getJsonObject(0);
    System.out.println("Json: " + JsonObject);

    // MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
    // JsonObject academicJsonObject = entries.getJsonObject(0).getJsonObject("academic");
    // mAcademicInformationBuilder.build(academicInformation, academicJsonObject, localCache);
    // mAcademicInformationManager.saveAcademicInformation(academicInformation);
    //
    // MutableAwardInformation awardInformation = new PersistentAwardInformation();
    // JsonObject awardJsonObject = entries.getJsonObject(0).getJsonObject("award");
    // mAwardInformationBuilder.build(awardInformation, awardJsonObject, localCache);
    // mAwardInformationManager.saveAwardInformation(awardInformation);
    //
    // MutableEmployeeInformation employeeInformation = new PersistentEmployeeInformation();
    // JsonObject employeeJsonObject = entries.getJsonObject(0).getJsonObject("employee");
    // mEmployeeInformationBuilder.build(employeeInformation, employeeJsonObject, localCache);
    // mEmployeeInformationManager.saveEmployeeInformation(employeeInformation);
    //
    // MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
    // JsonObject experienceJsonObject = entries.getJsonObject(0).getJsonObject("experience");
    // mExperienceInformationBuilder.build(experienceInformation, experienceJsonObject, localCache);
    // mExperienceInformationManager.saveExperienceInformation(experienceInformation);
    //
    // MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    // JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    // mPersonalInformationBuilder.build(personalInformation, personalJsonObject, localCache);
    // mPersonalInformationManager.savePersonalInformation(personalInformation);
    //
    // MutablePublicationInformation publicationInformation = new
    // PersistentPublicationInformation();
    // JsonObject publicationJsonObject = entries.getJsonObject(0).getJsonObject("publication");
    // mPublicationInformationBuilder.build(publicationInformation, publicationJsonObject,
    // localCache);
    // mPublicationInformationManager.savePublicationInformation(publicationInformation);
    //
    // MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
    // JsonObject trainingJsonObject = entries.getJsonObject(0).getJsonObject("training");
    // mTrainingInformationBuilder.build(trainingInformation, trainingJsonObject, localCache);
    // mTrainingInformationManager.saveTrainingInformation(trainingInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject jsonCreator(List<AcademicInformation> pAcademicInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    LocalCache localCache = new LocalCache();

    for(AcademicInformation academicInformation : pAcademicInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      mAcademicInformationBuilder.build(jsonObject, academicInformation, pUriInfo, localCache);
      children.add(jsonObject);
    }
    jObject.add("entries", children);
    localCache.invalidate();
    return jObject.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected EmployeeInformationManager getContentManager() {
    return mEmployeeInformationManager;
  }

  @Override
  protected EmployeeInformationBuilder getBuilder() {
    return mEmployeeInformationBuilder;
  }

  @Override
  protected String getETag(EmployeeInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
