package org.ums.resource.helper;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.EmployeeInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.*;
import org.ums.manager.registrar.*;
import org.ums.persistent.model.registrar.*;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeInformationResourceHelper extends
    ResourceHelper<ServiceInformation, MutableServiceInformation, Integer> {

  private static final Logger mLogger = LoggerFactory.getLogger(EmployeeInformationResourceHelper.class);

  @Autowired
  ServiceInformationManager mServiceInformationManager;

  @Autowired
  EmployeeInformationBuilder mEmployeeInformationBuilder;

  @Transactional
  public Response saveEmployeeInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray academicJsonArray = entries.getJsonObject(0).getJsonArray("academic");
    JsonArray awardJsonArray = entries.getJsonObject(0).getJsonArray("award");
    JsonArray publicationJsonArray = entries.getJsonObject(0).getJsonArray("publication");
    JsonArray trainingJsonArray = entries.getJsonObject(0).getJsonArray("training");
    JsonArray experienceJsonArray = entries.getJsonObject(0).getJsonArray("experience");

    int sizeOfAcademicJsonArray = academicJsonArray.size();
    int sizeOfAwardJsonArray = awardJsonArray.size();
    int sizeOfPublicationJsonArray = publicationJsonArray.size();
    int sizeOfTrainingJsonArray = trainingJsonArray.size();
    int sizeOfExperienceJsonArray = experienceJsonArray.size();

    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    // mEmployeeInformationBuilder.build(personalInformation, personalJsonObject, localCache);
    // mPersonalInformationManager.savePersonalInformation(personalInformation);

    System.out.println("Success in saving personal information");

    List<MutableAcademicInformation> mutableAcademicInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfAcademicJsonArray; i++) {
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      // mEmployeeInformationBuilder.build(academicInformation, academicJsonArray.getJsonObject(i),
      // localCache);
      mutableAcademicInformation.add(academicInformation);
    }
    // mAcademicInformationManager.saveAcademicInformation(mutableAcademicInformation);

    System.out.println("Success in saving academic information");

    List<MutableAwardInformation> mutableAwardInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfAwardJsonArray; i++) {
      MutableAwardInformation awardInformation = new PersistentAwardInformation();
      // mEmployeeInformationBuilder.build(awardInformation, awardJsonArray.getJsonObject(i),
      // localCache);
      mutableAwardInformation.add(awardInformation);
    }
    // mAwardInformationManager.saveAwardInformation(mutableAwardInformation);

    System.out.println("Success in saving award information");

    List<MutablePublicationInformation> mutablePublicationInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfPublicationJsonArray; i++) {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      // mEmployeeInformationBuilder.build(publicationInformation,
      // publicationJsonArray.getJsonObject(i), localCache);
      mutablePublicationInformation.add(publicationInformation);
    }
    // mPublicationInformationManager.savePublicationInformation(mutablePublicationInformation);

    System.out.println("Success in saving publication information");

    List<MutableExperienceInformation> mutableExperienceInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfExperienceJsonArray; i++) {
      MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
      // mEmployeeInformationBuilder.build(experienceInformation,
      // experienceJsonArray.getJsonObject(i), localCache);
      mutableExperienceInformation.add(experienceInformation);
    }
    // mExperienceInformationManager.saveExperienceInformation(mutableExperienceInformation);

    System.out.println("Success in saving experience information");

    List<MutableTrainingInformation> mutableTrainingInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfTrainingJsonArray; i++) {
      MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
      // mEmployeeInformationBuilder.build(trainingInformation, trainingJsonArray.getJsonObject(i),
      // localCache);
      mutableTrainingInformation.add(trainingInformation);
    }
    // mTrainingInformationManager.saveTrainingInformation(mutableTrainingInformation);

    System.out.println("Success in saving training information");

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response saveEmployeeServiceInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray serviceJsonArray = entries.getJsonObject(0).getJsonArray("service");
    int sizeOfServiceJsonArray = serviceJsonArray.size();

    List<MutableServiceInformation> mutableServiceInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfServiceJsonArray; i++) {
      MutableServiceInformation serviceInformation = new PersistentServiceInformation();
      mEmployeeInformationBuilder.build(serviceInformation, serviceJsonArray.getJsonObject(i), localCache);
      mutableServiceInformation.add(serviceInformation);
    }
    mServiceInformationManager.saveServiceInformation(mutableServiceInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private <T> JsonObject jsonCreator(List<T> pT, UriInfo pUriInfo) {
    JsonObjectBuilder jObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    LocalCache localCache = new LocalCache();

    for(T t : pT) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      // mEmployeeInformationBuilder.build(jsonObject, t, pUriInfo, localCache);
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
  protected ServiceInformationManager getContentManager() {
    return mServiceInformationManager;
  }

  @Override
  protected EmployeeInformationBuilder getBuilder() {
    return mEmployeeInformationBuilder;
  }

  @Override
  protected String getETag(ServiceInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
