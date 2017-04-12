package org.ums.registrar.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.EmployeeInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.immutable.registrar.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.*;
import org.ums.manager.registrar.*;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class EmployeeInformationResourceHelper extends
    ResourceHelper<EmployeeInformation, MutableEmployeeInformation, Integer> {

  private static final Logger mLogger = LoggerFactory.getLogger(EmployeeInformationResourceHelper.class);

  @Autowired
  AcademicInformationManager academicInformationManager;

  @Autowired
  AwardInformationManager awardInformationManager;

  @Autowired
  EmployeeInformationManager employeeInformationManager;

  @Autowired
  ExperienceInformationManager experienceInformationManager;

  @Autowired
  PersonalInformationManager personalInformationManager;

  @Autowired
  PublicationInformationManager publicationInformationManager;

  @Autowired
  TrainingInformationManager trainingInformationManager;

  @Autowired
  EmployeeInformationBuilder employeeInformationBuilder;

  public JsonObject getEmployeeAcademicInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        academicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  public JsonObject getEmployeeAwardInformation(final int pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation =
        academicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    return jsonCreator(pAcademicInformation, pUriInfo);
  }

  // public JsonObject getEmployeeInformation(final int pEmployeeId, final UriInfo pUriInfo) {
  // EmployeeInformation pEmployeeInformation =
  // employeeInformationManager.getEmployeeInformation(pEmployeeId);
  // return jsonCreator(pEmployeeInformation, pUriInfo);
  // }
  //
  // public JsonObject getEmployeeExperienceInformation(final int pEmployeeId, final UriInfo
  // pUriInfo) {
  // List<AcademicInformation> pAcademicInformation =
  // academicInformationBuilder.getEmployeeAcademicInformation(pEmployeeId);
  // return jsonCreator(pAcademicInformation, pUriInfo);
  // }
  //
  // public JsonObject getEmployeePersonalInformation(final int pEmployeeId, final UriInfo pUriInfo)
  // {
  // List<AcademicInformation> pAcademicInformation =
  // academicInformationBuilder.getEmployeeAcademicInformation(pEmployeeId);
  // return jsonCreator(pAcademicInformation, pUriInfo);
  // }
  //
  // public JsonObject getEmployeePublicationInformation(final int pEmployeeId, final UriInfo
  // pUriInfo) {
  // List<AcademicInformation> pAcademicInformation =
  // academicInformationBuilder.getEmployeeAcademicInformation(pEmployeeId);
  // return jsonCreator(pAcademicInformation, pUriInfo);
  // }
  //
  // public JsonObject getEmployeeTrainingInformation(final int pEmployeeId, final UriInfo pUriInfo)
  // {
  // List<AcademicInformation> pAcademicInformation =
  // academicInformationBuilder.getEmployeeAcademicInformation(pEmployeeId);
  // return jsonCreator(pAcademicInformation, pUriInfo);
  // }

  @Transactional
  public Response saveEmployeeInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    // System.out.println("I am in saveEmployeeInformation(). Now I am changinh this");
    // System.out.println("I am in saveEmployeeInformation(). askjdhiashdh bsakjd ghuias------");

    for(int i = 0; i < 10; i++) {
      i = i * 10;
    }

    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    // MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
    // JsonValue academicJsonObject = entries.getJsonObject(0).get("academic");
    // employeeInformationBuilder.build(academicInformation, academicJsonObject, localCache);
    // academicInformationManager.saveAcademicInformation(academicInformation);
    //
    // MutableAwardInformation awardInformation = new PersistentAwardInformation();
    // JsonObject awardJsonObject = entries.getJsonObject(0).getJsonObject("award");
    // employeeInformationBuilder.build(awardInformation, awardJsonObject, localCache);
    // awardInformationManager.saveAwardInformation(awardInformation);
    //
    // MutableEmployeeInformation employeeInformation = new PersistentEmployeeInformation();
    // JsonObject employeeJsonObject = entries.getJsonObject(0).getJsonObject("employee");
    // employeeInformationBuilder.build(employeeInformation, employeeJsonObject, localCache);
    // employeeInformationManager.saveEmployeeInformation(employeeInformation);
    //
    // MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
    // JsonObject experienceJsonObject = entries.getJsonObject(0).getJsonObject("experience");
    // employeeInformationBuilder.build(experienceInformation, experienceJsonObject, localCache);
    // experienceInformationManager.saveExperienceInformation(experienceInformation);
    //
    // MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    // JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    // employeeInformationBuilder.build(personalInformation, personalJsonObject, localCache);
    // personalInformationManager.savePersonalInformation(personalInformation);
    //
    // MutablePublicationInformation publicationInformation = new
    // PersistentPublicationInformation();
    // JsonObject publicationJsonObject = entries.getJsonObject(0).getJsonObject("publication");
    // employeeInformationBuilder.build(publicationInformation, publicationJsonObject, localCache);
    // publicationInformationManager.savePublicationInformation(publicationInformation);
    //
    // MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
    // JsonObject trainingJsonObject = entries.getJsonObject(0).getJsonObject("training");
    // employeeInformationBuilder.build(trainingInformation, trainingJsonObject, localCache);
    // trainingInformationManager.saveTrainingInformation(trainingInformation);
    //
    // Response.ResponseBuilder builder = Response.created(null);
    // builder.status(Response.Status.CREATED);
    return Response.ok().build();
  }

  private JsonObject jsonCreator(List<AcademicInformation> pAcademicInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    LocalCache localCache = new LocalCache();

    for(AcademicInformation academicInformation : pAcademicInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      employeeInformationBuilder.build(jsonObject, academicInformation, pUriInfo, localCache);
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
    return employeeInformationManager;
  }

  @Override
  protected EmployeeInformationBuilder getBuilder() {
    return employeeInformationBuilder;
  }

  @Override
  protected String getETag(EmployeeInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
