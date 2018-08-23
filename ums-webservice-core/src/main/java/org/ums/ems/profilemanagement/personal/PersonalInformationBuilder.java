package org.ums.ems.profilemanagement.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.NationalityBuilder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.*;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.CountryManager;
import org.ums.manager.common.DistrictManager;
import org.ums.manager.common.DivisionManager;
import org.ums.manager.common.ThanaManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.UriInfo;

@Component
public class PersonalInformationBuilder implements Builder<PersonalInformation, MutablePersonalInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Autowired
  private PersonalInformationManager mPersonalInformationManager;

  @Autowired
  private CountryManager mCountryManager;

  @Autowired
  private DivisionManager mDivisionManager;

  @Autowired
  private DistrictManager mDistrictManager;

  @Autowired
  private ThanaManager mThanaManager;

  private NationalityBuilder mNationalityBuilder;

  private NationalityType mNationalityType;

  private MaritalStatusType mMaritalStatusType;

  private ReligionType mReligionType;

  private RelationType mRelationType;

  private BloodGroupType mBloodGroupType;

  @Override
  public void build(JsonObjectBuilder pBuilder, PersonalInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    JsonObjectBuilder generalObjectBuilder = Json.createObjectBuilder();
    generalObjectBuilder.add("employeeId", pReadOnly.getId());
    generalObjectBuilder.add("salutation", pReadOnly.getSalutationId());
    JsonObjectBuilder salutationBuilder = Json.createObjectBuilder();
    salutationBuilder.add("id", pReadOnly.getSalutationId()).add("name",
        Salutation.get(pReadOnly.getSalutationId()).getLabel());
    generalObjectBuilder.add("salutation", salutationBuilder);
    generalObjectBuilder.add("name", pReadOnly.getName());
    generalObjectBuilder.add("fatherName", pReadOnly.getFatherName());
    generalObjectBuilder.add("motherName", pReadOnly.getMotherName());
    if(pReadOnly.getGender().equals(" ") || pReadOnly.getGender() == null) {
      generalObjectBuilder.add("gender", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder genderBuilder = Json.createObjectBuilder();
      if(pReadOnly.getGender().equals("M")) {
        genderBuilder.add("id", pReadOnly.getGender()).add("name", "Male");
      }
      else {
        genderBuilder.add("id", pReadOnly.getGender()).add("name", "Female");
      }
      generalObjectBuilder.add("gender", genderBuilder);
    }
    if(pReadOnly.getDateOfBirth().equals("") || pReadOnly.getDateOfBirth() == null) {
      generalObjectBuilder.add("dateOfBirth", "");
    }
    else {
      generalObjectBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));
    }
    if(pReadOnly.getNationalityId() == 0 || pReadOnly.getNationalityId() == null) {
      generalObjectBuilder.add("nationality", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder nationalityBuilder = Json.createObjectBuilder();
      nationalityBuilder.add("id", pReadOnly.getNationalityId()).add("name",
          mNationalityType.get(pReadOnly.getNationalityId()).getLabel());
      generalObjectBuilder.add("nationality", nationalityBuilder);
    }
    if(pReadOnly.getReligionId() == 0 || pReadOnly.getReligionId() == null) {
      generalObjectBuilder.add("religion", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder religionBuilder = Json.createObjectBuilder();
      religionBuilder.add("id", pReadOnly.getReligionId()).add("name",
          mReligionType.get(pReadOnly.getReligionId()).getLabel());
      generalObjectBuilder.add("religion", religionBuilder);
    }
    if(pReadOnly.getMaritalStatusId() == 0 || pReadOnly.getMaritalStatusId() == null) {
      generalObjectBuilder.add("maritalStatus", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder maritalStatusBuilder = Json.createObjectBuilder();
      maritalStatusBuilder.add("id", pReadOnly.getMaritalStatusId()).add("name",
          mMaritalStatusType.get(pReadOnly.getMaritalStatusId()).getLabel());
      generalObjectBuilder.add("maritalStatus", maritalStatusBuilder);
    }
    generalObjectBuilder.add("spouseName", pReadOnly.getSpouseName() == null ? "" : pReadOnly.getSpouseName());
    generalObjectBuilder.add("nidNo", pReadOnly.getNidNo() == null ? "" : pReadOnly.getNidNo());
    if(pReadOnly.getBloodGroupId() == 0 || pReadOnly.getBloodGroupId() == null) {
      generalObjectBuilder.add("bloodGroup", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder bloodGroupBuilder = Json.createObjectBuilder();
      bloodGroupBuilder.add("id", pReadOnly.getBloodGroupId()).add("name",
          mBloodGroupType.get(pReadOnly.getBloodGroupId()).getLabel());
      generalObjectBuilder.add("bloodGroup", bloodGroupBuilder);
    }
    generalObjectBuilder.add("spouseNidNo", pReadOnly.getSpouseNidNo() == null ? "" : pReadOnly.getSpouseNidNo());
    generalObjectBuilder.add("website", pReadOnly.getWebsite() == null ? "" : pReadOnly.getWebsite());

    pBuilder.add("general", generalObjectBuilder);

    JsonObjectBuilder contactObjectBuilder = Json.createObjectBuilder();
    contactObjectBuilder.add("organizationalEmail",
        pReadOnly.getOrganizationalEmail() == null ? "" : pReadOnly.getOrganizationalEmail());
    contactObjectBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
    contactObjectBuilder.add("mobile", pReadOnly.getMobileNumber());
    contactObjectBuilder.add("phone", pReadOnly.getPhoneNumber() == null ? "" : pReadOnly.getPhoneNumber());
    contactObjectBuilder.add("preAddressLine1", pReadOnly.getPresentAddressLine1());
    contactObjectBuilder.add("preAddressLine2",
        pReadOnly.getPresentAddressLine2() == null ? "" : pReadOnly.getPresentAddressLine2());
    JsonObjectBuilder presentCountryBuilder = Json.createObjectBuilder();
    presentCountryBuilder.add("id", pReadOnly.getPresentAddressCountryId()).add("name",
        mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName());
    contactObjectBuilder.add("preAddressCountry", presentCountryBuilder);
    if(mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName().equals("Bangladesh")) {
      if(pReadOnly.getPresentAddressDivisionId() == 0 || pReadOnly.getPresentAddressDivisionId() == null) {
        contactObjectBuilder.add("preAddressDivision", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentDivisionBuilder = Json.createObjectBuilder();
        presentDivisionBuilder.add("id", pReadOnly.getPresentAddressDivisionId()).add("name",
            mDivisionManager.get(pReadOnly.getPresentAddressDivisionId()).getDivisionName());
        contactObjectBuilder.add("preAddressDivision", presentDivisionBuilder);
      }
      if(pReadOnly.getPresentAddressDistrictId() == 0 || pReadOnly.getPresentAddressDistrictId() == null) {
        contactObjectBuilder.add("preAddressDistrict", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentDistrictBuilder = Json.createObjectBuilder();
        presentDistrictBuilder.add("id", pReadOnly.getPresentAddressDistrictId()).add("name",
            mDistrictManager.get(pReadOnly.getPresentAddressDistrictId()).getDistrictName());
        contactObjectBuilder.add("preAddressDistrict", presentDistrictBuilder);
      }
      if(pReadOnly.getPresentAddressThanaId() == 0 || pReadOnly.getPresentAddressThanaId() == null) {
        contactObjectBuilder.add("preAddressThana", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentThanaBuilder = Json.createObjectBuilder();
        presentThanaBuilder.add("id", pReadOnly.getPresentAddressThanaId()).add("name",
            mThanaManager.get(pReadOnly.getPresentAddressThanaId()).getThanaName());
        contactObjectBuilder.add("preAddressThana", presentThanaBuilder);
      }
    }
    else {
      contactObjectBuilder.add("preAddressDivision", JsonValue.NULL);
      contactObjectBuilder.add("preAddressDistrict", JsonValue.NULL);
      contactObjectBuilder.add("preAddressThana", JsonValue.NULL);
    }
    contactObjectBuilder.add("preAddressPostCode",
        pReadOnly.getPresentAddressPostCode() == null ? "" : pReadOnly.getPresentAddressPostCode());
    contactObjectBuilder.add("perAddressLine1", pReadOnly.getPermanentAddressLine1());
    contactObjectBuilder.add("perAddressLine2",
        pReadOnly.getPermanentAddressLine2() == null ? "" : pReadOnly.getPermanentAddressLine2());
    JsonObjectBuilder permanentCountryBuilder = Json.createObjectBuilder();
    permanentCountryBuilder.add("id", pReadOnly.getPermanentAddressCountryId()).add("name",
        mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName());
    contactObjectBuilder.add("perAddressCountry", permanentCountryBuilder);
    if(mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName().equals("Bangladesh")) {
      if(pReadOnly.getPermanentAddressDivisionId() == 0 || pReadOnly.getPermanentAddressDivisionId() == null) {
        contactObjectBuilder.add("perAddressDivision", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentDivisionBuilder = Json.createObjectBuilder();
        permanentDivisionBuilder.add("id", pReadOnly.getPermanentAddressDivisionId()).add("name",
            mDivisionManager.get(pReadOnly.getPermanentAddressDivisionId()).getDivisionName());
        contactObjectBuilder.add("perAddressDivision", permanentDivisionBuilder);
      }
      if(pReadOnly.getPermanentAddressDistrictId() == 0 || pReadOnly.getPermanentAddressDistrictId() == null) {
        contactObjectBuilder.add("perAddressDistrict", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentDistrictBuilder = Json.createObjectBuilder();
        permanentDistrictBuilder.add("id", pReadOnly.getPermanentAddressDistrictId()).add("name",
            mDistrictManager.get(pReadOnly.getPermanentAddressDistrictId()).getDistrictName());
        contactObjectBuilder.add("perAddressDistrict", permanentDistrictBuilder);
      }
      if(pReadOnly.getPermanentAddressThanaId() == 0 || pReadOnly.getPermanentAddressThanaId() == null) {
        contactObjectBuilder.add("perAddressThana", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentThanaBuilder = Json.createObjectBuilder();
        permanentThanaBuilder.add("id", pReadOnly.getPermanentAddressThanaId()).add("name",
            mThanaManager.get(pReadOnly.getPermanentAddressThanaId()).getThanaName());
        contactObjectBuilder.add("perAddressThana", permanentThanaBuilder);
      }
    }
    else {
      contactObjectBuilder.add("perAddressDivision", JsonValue.NULL);
      contactObjectBuilder.add("perAddressDistrict", JsonValue.NULL);
      contactObjectBuilder.add("perAddressThana", JsonValue.NULL);
    }
    contactObjectBuilder.add("perAddressPostCode",
        pReadOnly.getPermanentAddressPostCode() == null ? "" : pReadOnly.getPermanentAddressPostCode());

    pBuilder.add("contact", contactObjectBuilder);

    JsonObjectBuilder emergencyContactObjectBuilder = Json.createObjectBuilder();
    emergencyContactObjectBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());
    if(pReadOnly.getEmergencyContactRelationId() == 0 || pReadOnly.getEmergencyContactRelationId() == null) {
      emergencyContactObjectBuilder.add("emergencyContactRelation", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder relationBuilder = Json.createObjectBuilder();
      relationBuilder.add("id", pReadOnly.getEmergencyContactRelationId()).add("name",
          mRelationType.get(pReadOnly.getEmergencyContactRelationId()).getLabel());
      emergencyContactObjectBuilder.add("emergencyContactRelation", relationBuilder);
    }
    emergencyContactObjectBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());
    emergencyContactObjectBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress() == null ? ""
        : pReadOnly.getEmergencyContactAddress());

    pBuilder.add("emergencyContact", emergencyContactObjectBuilder);
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    PersonalInformation personalInformation = mPersonalInformationManager.get(pJsonObject.getString("employeeId"));

    if(pJsonObject.getString("type").equals("general")) {
      pMutable.setId(pJsonObject.getString("employeeId"));
      pMutable.setSalutationId(pJsonObject.getJsonObject("salutation").getInt("id"));
      pMutable.setName(pJsonObject.getString("name").trim());
      pMutable.setFatherName(pJsonObject.getString("fatherName"));
      pMutable.setMotherName(pJsonObject.getString("motherName"));
      pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
      pMutable.setDateOfBirth(mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
      pMutable.setNationalityId(pJsonObject.getJsonObject("nationality").getInt("id"));
      pMutable.setReligionId(pJsonObject.getJsonObject("religion").getInt("id"));
      pMutable.setMaritalStatusId(pJsonObject.getJsonObject("maritalStatus").getInt("id"));
      pMutable.setSpouseName(pJsonObject.containsKey("spouseName") ? pJsonObject.getString("spouseName") : "");
      pMutable.setNidNo(pJsonObject.containsKey("nidNo") ? pJsonObject.getString("nidNo") : "");
      pMutable.setBloodGroupId(pJsonObject.getJsonObject("bloodGroup").getInt("id"));
      pMutable.setSpouseNidNo(pJsonObject.containsKey("spouseNidNo") ? pJsonObject.getString("spouseNidNo") : "");
      pMutable.setWebsite(pJsonObject.containsKey("website") ? pJsonObject.getString("website") : "");
    }
    else {
      pMutable.setId(personalInformation.getId());
      pMutable.setName(personalInformation.getName());
      pMutable.setFatherName(personalInformation.getFatherName());
      pMutable.setMotherName(personalInformation.getMotherName());
      pMutable.setGender(personalInformation.getGender());
      pMutable.setDateOfBirth(personalInformation.getDateOfBirth());
      pMutable.setNationalityId(personalInformation.getNationalityId());
      pMutable.setReligionId(personalInformation.getReligionId());
      pMutable.setMaritalStatusId(personalInformation.getMaritalStatusId());
      pMutable.setSpouseName(personalInformation.getSpouseName());
      pMutable.setNidNo(personalInformation.getNidNo());
      pMutable.setBloodGroupId(personalInformation.getBloodGroupId());
      pMutable.setSpouseNidNo(personalInformation.getSpouseNidNo());
      pMutable.setWebsite(personalInformation.getWebsite());
    }

    if(pJsonObject.getString("type").equals("contact")) {
      pMutable.setOrganizationalEmail(pJsonObject.containsKey("organizationalEmail") ? pJsonObject
          .getString("organizationalEmail") : "");
      pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
      pMutable.setMobileNumber(pJsonObject.getString("mobile"));
      pMutable.setPhoneNumber(pJsonObject.containsKey("phone") ? pJsonObject.getString("phone") : "");
      pMutable.setPresentAddressLine1(pJsonObject.getString("preAddressLine1"));
      pMutable.setPresentAddressLine2(pJsonObject.containsKey("preAddressLine2") ? pJsonObject
          .getString("preAddressLine2") : "");
      pMutable.setPresentAddressCountryId(pJsonObject.getJsonObject("preAddressCountry").getInt("id"));
      if(pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
        pMutable.setPresentAddressDivisionId(pJsonObject.getJsonObject("preAddressDivision").getInt("id"));
        pMutable.setPresentAddressDistrictId(pJsonObject.getJsonObject("preAddressDistrict").getInt("id"));
        pMutable.setPresentAddressThanaId(pJsonObject.getJsonObject("preAddressThana").getInt("id"));
        pMutable.setPresentAddressPostCode(pJsonObject.containsKey("preAddressPostCode") ? pJsonObject
            .getString("preAddressPostCode") : "");
      }
      else {
        pMutable.setPresentAddressDivision(null);
        pMutable.setPresentAddressDistrict(null);
        pMutable.setPresentAddressThana(null);
        pMutable.setPresentAddressPostCode("");
      }
      pMutable.setPermanentAddressLine1(pJsonObject.getString("perAddressLine1"));
      pMutable.setPermanentAddressLine2(pJsonObject.containsKey("preAddressLine2") ? pJsonObject
          .getString("preAddressLine2") : "");
      pMutable.setPermanentAddressCountryId(pJsonObject.getJsonObject("perAddressCountry").getInt("id"));
      if(pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
        pMutable.setPermanentAddressDivisionId(pJsonObject.getJsonObject("perAddressDivision").getInt("id"));
        pMutable.setPermanentAddressDistrictId(pJsonObject.getJsonObject("perAddressDistrict").getInt("id"));
        pMutable.setPermanentAddressThanaId(pJsonObject.getJsonObject("perAddressThana").getInt("id"));
        pMutable.setPermanentAddressPostCode(pJsonObject.containsKey("perAddressPostCode") ? pJsonObject
            .getString("perAddressPostCode") : "");
      }
      else {
        pMutable.setPermanentAddressDivision(null);
        pMutable.setPermanentAddressDistrict(null);
        pMutable.setPermanentAddressThana(null);
        pMutable.setPermanentAddressPostCode("");
      }
    }
    else {
      pMutable.setOrganizationalEmail(personalInformation.getOrganizationalEmail());
      pMutable.setPersonalEmail(personalInformation.getPersonalEmail());
      pMutable.setMobileNumber(personalInformation.getMobileNumber());
      pMutable.setPhoneNumber(personalInformation.getPhoneNumber());
      pMutable.setPresentAddressLine1(personalInformation.getPresentAddressLine1());
      pMutable.setPresentAddressLine2(personalInformation.getPresentAddressLine2());
      pMutable.setPresentAddressCountryId(personalInformation.getPresentAddressCountryId());
      pMutable.setPresentAddressDivisionId(personalInformation.getPresentAddressDivisionId());
      pMutable.setPresentAddressDistrictId(personalInformation.getPresentAddressDistrictId());
      pMutable.setPresentAddressThanaId(personalInformation.getPresentAddressThanaId());
      pMutable.setPresentAddressPostCode(personalInformation.getPresentAddressPostCode());
      pMutable.setPermanentAddressLine1(personalInformation.getPermanentAddressLine1());
      pMutable.setPermanentAddressLine2(personalInformation.getPermanentAddressLine2());
      pMutable.setPermanentAddressCountryId(personalInformation.getPermanentAddressCountryId());
      pMutable.setPermanentAddressDivisionId(personalInformation.getPermanentAddressDivisionId());
      pMutable.setPermanentAddressDistrictId(personalInformation.getPermanentAddressDistrictId());
      pMutable.setPermanentAddressThanaId(personalInformation.getPermanentAddressThanaId());
      pMutable.setPermanentAddressPostCode(personalInformation.getPermanentAddressPostCode());

    }
    if(pJsonObject.getString("type").equals("emergencyContact")) {
      pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
      pMutable.setEmergencyContactRelationId(pJsonObject.getJsonObject("emergencyContactRelation").getInt("id"));
      pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
      pMutable.setEmergencyContactAddress(pJsonObject.containsKey("emergencyContactAddress") ? pJsonObject
          .getString("emergencyContactAddress") : "");
    }
    else {
      pMutable.setEmergencyContactName(personalInformation.getEmergencyContactName());
      pMutable.setEmergencyContactRelationId(personalInformation.getEmergencyContactRelationId());
      pMutable.setEmergencyContactPhone(personalInformation.getEmergencyContactPhone());
      pMutable.setEmergencyContactAddress(personalInformation.getEmergencyContactAddress());
    }
  }
}
