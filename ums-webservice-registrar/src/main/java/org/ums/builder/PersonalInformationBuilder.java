package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.*;
import org.ums.persistent.model.common.*;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class PersonalInformationBuilder implements Builder<PersonalInformation, MutablePersonalInformation> {

  @Autowired
  UserManager userManager;

  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  NationalityManager mNationalityManager;

  @Autowired
  MaritalStatusManager mMaritalStatusManager;

  @Autowired
  ReligionManager mReligionManager;

  @Autowired
  RelationTypeManager mRelationTypeManager;

  @Autowired
  BloodGroupManager mBloodGroupManager;

  @Autowired
  CountryManager mCountryManager;

  @Autowired
  DivisionManager mDivisionManager;

  @Autowired
  DistrictManager mDistrictManager;

  @Autowired
  ThanaManager mThanaManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, PersonalInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    PersistentPersonalInformation personalInformation = new PersistentPersonalInformation();

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    pBuilder.add("employeeId", pReadOnly.getId());

    pBuilder.add("firstName", pReadOnly.getFirstName());

    pBuilder.add("lastName", pReadOnly.getLastName());

    pBuilder.add("fatherName", pReadOnly.getFatherName());

    pBuilder.add("motherName", pReadOnly.getMotherName());

    pBuilder.add("gender", pReadOnly.getGender());

    pBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));

    // System.out.println(pReadOnly.getNationalityId());
    //
    // System.out.println(mNationalityManager.get(99));

    // pBuilder.add("nationality", (JsonObjectBuilder)
    // mNationalityManager.get(personalInformation.getNationalityId()));
    //
    // pBuilder.add("religion", (JsonObjectBuilder)
    // mReligionManager.get(personalInformation.getReligionId()));
    //
    // pBuilder.add("maritalStatus",
    // (JsonObjectBuilder) mMaritalStatusManager.get(personalInformation.getMaritalStatusId()));
    //
    // if(pReadOnly.getSpouseName() != null) {
    // pBuilder.add("spouseName", pReadOnly.getSpouseName());
    // }
    // else {
    // pBuilder.add("spouseName", "");
    // }
    //
    // if(pReadOnly.getNationalId() != null) {
    // pBuilder.add("nationalIdNo", pReadOnly.getNationalId());
    // }
    // else {
    // pBuilder.add("nationalIdNo", "");
    // }
    //
    // pBuilder.add("bloodGroup", (JsonObjectBuilder)
    // mBloodGroupManager.get(personalInformation.getBloodGroupId()));
    //
    // if(pReadOnly.getSpouseNationalId() != null) {
    // pBuilder.add("spouseNationalIdNo", pReadOnly.getSpouseNationalId());
    // }
    // else {
    // pBuilder.add("spouseNationalIdNo", "");
    // }
    //
    // if(pReadOnly.getWebsite() != null) {
    // pBuilder.add("personalWebsite", pReadOnly.getWebsite());
    // }
    // else {
    // pBuilder.add("personalWebsite", "");
    // }
    //
    // if(pReadOnly.getOrganizationalEmail() != null) {
    // pBuilder.add("organizationalEmail", pReadOnly.getOrganizationalEmail());
    // }
    // else {
    // pBuilder.add("organizationalEmail", "");
    // }
    //
    // pBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
    //
    // pBuilder.add("mobile", pReadOnly.getMobileNumber());
    //
    // if(pReadOnly.getPhoneNumber() != null) {
    // pBuilder.add("phone", pReadOnly.getPhoneNumber());
    // }
    // else {
    // pBuilder.add("phone", "");
    // }
    //
    // pBuilder.add("preAddressHouse", pReadOnly.getPresentAddressHouse());
    //
    // if(pReadOnly.getPresentAddressRoad() != null) {
    // pBuilder.add("preAddressRoad", pReadOnly.getPresentAddressRoad());
    // }
    // else {
    // pBuilder.add("preAddressRoad", "");
    // }
    //
    // if(pReadOnly.getPresentAddressThana() != null) {
    // pBuilder.add("preAddressThana",
    // (JsonObjectBuilder) mThanaManager.get(personalInformation.getPresentAddressThanaId()));
    // }
    // else {
    // pBuilder.add("preAddressThana", "");
    // }
    //
    // if(pReadOnly.getPresentAddressZip() != null) {
    // pBuilder.add("preAddressPostOfficeNo", pReadOnly.getPresentAddressZip());
    // }
    // else {
    // pBuilder.add("preAddressPostOfficeNo", "");
    // }
    //
    // if(pReadOnly.getPresentAddressDistrict() != null) {
    // pBuilder.add("preAddressDistrict",
    // (JsonObjectBuilder) mDistrictManager.get(personalInformation.getPresentAddressDistrictId()));
    // }
    // else {
    // pBuilder.add("preAddressDistrict", "");
    // }
    //
    // if(pReadOnly.getPresentAddressDivision() != null) {
    // pBuilder.add("preAddressDivision",
    // (JsonObjectBuilder) mDivisionManager.get(personalInformation.getPresentAddressDivisionId()));
    // }
    // else {
    // pBuilder.add("preAddressDivision", "");
    // }
    //
    // pBuilder.add("preAddressCountry",
    // (JsonObjectBuilder) mCountryManager.get(personalInformation.getPresentAddressCountryId()));
    //
    // pBuilder.add("perAddressHouse", pReadOnly.getPermanentAddressHouse());
    //
    // if(pReadOnly.getPermanentAddressRoad() != null) {
    // pBuilder.add("perAddressRoad", pReadOnly.getPermanentAddressRoad());
    // }
    // else {
    // pBuilder.add("perAddressRoad", "");
    // }
    //
    // if(pReadOnly.getPresentAddressThana() != null) {
    // pBuilder.add("perAddressThana",
    // (JsonObjectBuilder) mThanaManager.get(personalInformation.getPermanentAddressThanaId()));
    // }
    // else {
    // pBuilder.add("perAddressThana", "");
    // }
    //
    // if(pReadOnly.getPermanentAddressZip() != null) {
    // pBuilder.add("perAddressPostOfficeNo", pReadOnly.getPermanentAddressZip());
    // }
    // else {
    // pBuilder.add("perAddressPostOfficeNo", "");
    // }
    //
    // if(pReadOnly.getPermanentAddressDistrict() != null) {
    // pBuilder.add("perAddressDistrict",
    // (JsonObjectBuilder)
    // mDistrictManager.get(personalInformation.getPermanentAddressDistrictId()));
    // }
    // else {
    // pBuilder.add("perAddressDistrict", "");
    // }
    //
    // if(pReadOnly.getPermanentAddressDivision() != null) {
    // pBuilder.add("perAddressDivision",
    // (JsonObjectBuilder)
    // mDivisionManager.get(personalInformation.getPermanentAddressDivisionId()));
    // }
    // else {
    // pBuilder.add("perAddressDivision", "");
    // }
    //
    // pBuilder.add("perAddressCountry",
    // (JsonObjectBuilder) mCountryManager.get(personalInformation.getPermanentAddressCountryId()));
    //
    // pBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());
    //
    // pBuilder.add("emergencyContactRelation",
    // (JsonObjectBuilder)
    // mRelationTypeManager.get(personalInformation.getEmergencyContactRelationId()));
    //
    // pBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());
    //
    // if(pReadOnly.getEmergencyContactAddress() != null) {
    // pBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress());
    // }
    // else {
    // pBuilder.add("emergencyContactAddress", "");
    // }
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    PersistentNationality persistentNationality = new PersistentNationality();
    PersistentMaritalStatus persistentMaritalStatus = new PersistentMaritalStatus();
    PersistentBloodGroup persistentBloodGroup = new PersistentBloodGroup();
    PersistentReligion persistentReligion = new PersistentReligion();
    PersistentRelationType persistentRelationType = new PersistentRelationType();
    PersistentCountry prePersistentCountry = new PersistentCountry();
    PersistentDivision prePersistentDivision = new PersistentDivision();
    PersistentDistrict prePersistentDistrict = new PersistentDistrict();
    PersistentThana prePersistentThana = new PersistentThana();
    PersistentCountry perPersistentCountry = new PersistentCountry();
    PersistentDivision perPersistentDivision = new PersistentDivision();
    PersistentDistrict perPersistentDistrict = new PersistentDistrict();
    PersistentThana perPersistentThana = new PersistentThana();
    persistentNationality.setId(pJsonObject.getJsonObject("nationality").getInt("id"));
    persistentMaritalStatus.setId(pJsonObject.getJsonObject("maritalStatus").getInt("id"));
    persistentBloodGroup.setId(pJsonObject.getJsonObject("bloodGroup").getInt("id"));
    persistentReligion.setId(pJsonObject.getJsonObject("religion").getInt("id"));
    persistentRelationType.setId(pJsonObject.getJsonObject("emergencyContactRelation").getInt("id"));
    prePersistentCountry.setId(pJsonObject.getJsonObject("preAddressCountry").getInt("id"));
    prePersistentDivision.setId(pJsonObject.getJsonObject("preAddressDivision").getInt("id"));
    prePersistentDistrict.setId(pJsonObject.getJsonObject("preAddressDistrict").getInt("id"));
    prePersistentThana.setId(pJsonObject.getJsonObject("preAddressThana").getInt("id"));
    perPersistentCountry.setId(pJsonObject.getJsonObject("perAddressCountry").getInt("id"));
    perPersistentDivision.setId(pJsonObject.getJsonObject("perAddressDivision").getInt("id"));
    perPersistentDistrict.setId(pJsonObject.getJsonObject("perAddressDistrict").getInt("id"));
    perPersistentThana.setId(pJsonObject.getJsonObject("perAddressThana").getInt("id"));

    pMutable.setId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setFirstName(pJsonObject.getString("firstName"));
    pMutable.setLastName(pJsonObject.getString("lastName"));
    pMutable.setFatherName(pJsonObject.getString("fatherName"));
    pMutable.setMotherName(pJsonObject.getString("motherName"));
    pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
    pMutable.setDateOfBirth(mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
    pMutable.setNationality(persistentNationality);
    pMutable.setReligion(persistentReligion);
    pMutable.setMaritalStatus(persistentMaritalStatus);
    if(!pJsonObject.containsKey("spouseName")) {
      pMutable.setSpouseName("");
    }
    else {
      pMutable.setSpouseName(pJsonObject.getString("spouseName"));
    }
    if(!pJsonObject.containsKey("nationalId")) {
      pMutable.setNationalId("");
    }
    else {
      pMutable.setNationalId(pJsonObject.getString("nationalIdNo"));
    }
    pMutable.setBloodGroup(persistentBloodGroup);
    if(!pJsonObject.containsKey("spouseNationalIdNo")) {
      pMutable.setSpouseNationalId("");
    }
    else {
      pMutable.setSpouseNationalId(pJsonObject.getString("spouseNationalIdNo"));
    }
    if(!pJsonObject.containsKey("personalWebsite")) {
      pMutable.setWebsite("");
    }
    else {
      pMutable.setWebsite(pJsonObject.getString("personalWebsite"));
    }
    if(!pJsonObject.containsKey("organizationalEmail")) {
      pMutable.setOrganizationalEmail("");
    }
    else {
      pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
    }
    pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
    pMutable.setMobileNumber(pJsonObject.getString("mobile"));
    if(!pJsonObject.containsKey("phone")) {
      pMutable.setPhoneNumber("");
    }
    else {
      pMutable.setPhoneNumber(pJsonObject.getString("phone"));
    }
    pMutable.setPresentAddressHouse(pJsonObject.getString("preAddressHouse"));
    if(!pJsonObject.containsKey("preAddressRoad")) {
      pMutable.setPresentAddressRoad("");
    }
    else {
      pMutable.setPresentAddressRoad(pJsonObject.getString("preAddressRoad"));
    }
    if(!pJsonObject.containsKey("preAddressPostOfficeNo")) {
      pMutable.setPresentAddressZip(null);
    }
    else {
      pMutable.setPresentAddressZip(pJsonObject.getInt("preAddressPostOfficeNo"));
    }
    if(pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
      pMutable.setPresentAddressThana(prePersistentThana);
      pMutable.setPresentAddressDistrict(prePersistentDistrict);
      pMutable.setPresentAddressDivision(prePersistentDivision);
    }
    else {
      pMutable.setPresentAddressThana(null);
      pMutable.setPresentAddressDistrict(null);
      pMutable.setPresentAddressDivision(null);
    }
    pMutable.setPresentAddressCountry(prePersistentCountry);
    pMutable.setPermanentAddressHouse(pJsonObject.getString("perAddressHouse"));
    if(!pJsonObject.containsKey("perAddressRoad")) {
      pMutable.setPermanentAddressRoad("");
    }
    else {
      pMutable.setPermanentAddressRoad(pJsonObject.getString("perAddressRoad"));
    }
    if(!pJsonObject.containsKey("perAddressPostOfficeNo")) {
      pMutable.setPermanentAddressZip(null);
    }
    else {
      pMutable.setPermanentAddressZip(pJsonObject.getInt("perAddressPostOfficeNo"));
    }
    if(pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
      pMutable.setPermanentAddressThana(perPersistentThana);
      pMutable.setPermanentAddressDistrict(perPersistentDistrict);
      pMutable.setPermanentAddressDivision(perPersistentDivision);
    }
    else {
      pMutable.setPermanentAddressThana(null);
      pMutable.setPermanentAddressDistrict(null);
      pMutable.setPermanentAddressDivision(null);
    }
    pMutable.setPermanentAddressCountry(perPersistentCountry);
    pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    pMutable.setEmergencyContactRelation(persistentRelationType);
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    if(!pJsonObject.containsKey("emergencyContactAddress")) {
      pMutable.setEmergencyContactAddress("");
    }
    else {
      pMutable.setEmergencyContactAddress(pJsonObject.getString("emergencyContactAddress"));
    }
  }
}
