package org.ums.employee.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.NationalityBuilder;
import org.ums.cache.LocalCache;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.MutablePersonalInformation;
import org.ums.enums.common.*;
import org.ums.enums.common.RelationType;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.*;

import javax.json.*;
import javax.ws.rs.core.UriInfo;

@Component
public class PersonalInformationBuilder implements Builder<PersonalInformation, MutablePersonalInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

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
    pBuilder.add("employeeId", pReadOnly.getId());
    pBuilder.add("firstName", pReadOnly.getFirstName());
    pBuilder.add("lastName", pReadOnly.getLastName());
    pBuilder.add("fatherName", pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName());
    if(pReadOnly.getGender().equals(" ") || pReadOnly.getGender() == null) {
      pBuilder.add("gender", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder genderBuilder = Json.createObjectBuilder();
      if(pReadOnly.getGender().equals("M")) {
        genderBuilder.add("id", pReadOnly.getGender()).add("name", "Male");
      }
      else {
        genderBuilder.add("id", pReadOnly.getGender()).add("name", "Female");
      }
      pBuilder.add("gender", genderBuilder);
    }
    if(pReadOnly.getDateOfBirth().equals("") || pReadOnly.getDateOfBirth() == null) {
      pBuilder.add("dateOfBirth", "");
    }
    else {
      pBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));
    }
    if(pReadOnly.getNationalityId() == 0 || pReadOnly.getNationalityId() == null) {
      pBuilder.add("nationality", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder nationalityBuilder = Json.createObjectBuilder();
      nationalityBuilder.add("id", pReadOnly.getNationalityId()).add("name",
          mNationalityType.get(pReadOnly.getNationalityId()).getLabel());
      pBuilder.add("nationality", nationalityBuilder);
    }
    if(pReadOnly.getReligionId() == 0 || pReadOnly.getReligionId() == null) {
      pBuilder.add("religion", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder religionBuilder = Json.createObjectBuilder();
      religionBuilder.add("id", pReadOnly.getReligionId()).add("name",
          mReligionType.get(pReadOnly.getReligionId()).getLabel());
      pBuilder.add("religion", religionBuilder);
    }
    if(pReadOnly.getMaritalStatusId() == 0 || pReadOnly.getMaritalStatusId() == null) {
      pBuilder.add("maritalStatus", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder maritalStatusBuilder = Json.createObjectBuilder();
      maritalStatusBuilder.add("id", pReadOnly.getMaritalStatusId()).add("name",
          mMaritalStatusType.get(pReadOnly.getMaritalStatusId()).getLabel());
      pBuilder.add("maritalStatus", maritalStatusBuilder);
    }
    pBuilder.add("spouseName", pReadOnly.getSpouseName() == null ? "" : pReadOnly.getSpouseName());
    pBuilder.add("nidNo", pReadOnly.getNidNo() == null ? "" : pReadOnly.getNidNo());
    if(pReadOnly.getBloodGroupId() == 0 || pReadOnly.getBloodGroupId() == null) {
      pBuilder.add("bloodGroup", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder bloodGroupBuilder = Json.createObjectBuilder();
      bloodGroupBuilder.add("id", pReadOnly.getBloodGroupId()).add("name",
          mBloodGroupType.get(pReadOnly.getBloodGroupId()).getLabel());
      pBuilder.add("bloodGroup", bloodGroupBuilder);
    }
    pBuilder.add("spouseNidNo", pReadOnly.getSpouseNidNo() == null ? "" : pReadOnly.getSpouseNidNo());
    pBuilder.add("website", pReadOnly.getWebsite() == null ? "" : pReadOnly.getWebsite());
    pBuilder.add("organizationalEmail",
        pReadOnly.getOrganizationalEmail() == null ? "" : pReadOnly.getOrganizationalEmail());
    pBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
    pBuilder.add("mobile", pReadOnly.getMobileNumber());
    pBuilder.add("phone", pReadOnly.getPhoneNumber() == null ? "" : pReadOnly.getPhoneNumber());
    pBuilder.add("preAddressLine1", pReadOnly.getPresentAddressLine1());
    pBuilder.add("preAddressLine2",
        pReadOnly.getPresentAddressLine2() == null ? "" : pReadOnly.getPresentAddressLine2());
    JsonObjectBuilder presentCountryBuilder = Json.createObjectBuilder();
    presentCountryBuilder.add("id", pReadOnly.getPresentAddressCountryId()).add("name",
        mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName());
    pBuilder.add("preAddressCountry", presentCountryBuilder);
    if(mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName().equals("Bangladesh")) {
      if(pReadOnly.getPresentAddressDivisionId() == 0 || pReadOnly.getPresentAddressDivisionId() == null) {
        pBuilder.add("preAddressDivision", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentDivisionBuilder = Json.createObjectBuilder();
        presentDivisionBuilder.add("id", pReadOnly.getPresentAddressDivisionId()).add("name",
            mDivisionManager.get(pReadOnly.getPresentAddressDivisionId()).getDivisionName());
        pBuilder.add("preAddressDivision", presentDivisionBuilder);
      }
      if(pReadOnly.getPresentAddressDistrictId() == 0 || pReadOnly.getPresentAddressDistrictId() == null) {
        pBuilder.add("preAddressDistrict", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentDistrictBuilder = Json.createObjectBuilder();
        presentDistrictBuilder.add("id", pReadOnly.getPresentAddressDistrictId()).add("name",
            mDistrictManager.get(pReadOnly.getPresentAddressDistrictId()).getDistrictName());
        pBuilder.add("preAddressDistrict", presentDistrictBuilder);
      }
      if(pReadOnly.getPresentAddressThanaId() == 0 || pReadOnly.getPresentAddressThanaId() == null) {
        pBuilder.add("preAddressThana", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder presentThanaBuilder = Json.createObjectBuilder();
        presentThanaBuilder.add("id", pReadOnly.getPresentAddressThanaId()).add("name",
            mThanaManager.get(pReadOnly.getPresentAddressThanaId()).getThanaName());
        pBuilder.add("preAddressThana", presentThanaBuilder);
      }
    }
    else {
      pBuilder.add("preAddressDivision", JsonValue.NULL);
      pBuilder.add("preAddressDistrict", JsonValue.NULL);
      pBuilder.add("preAddressThana", JsonValue.NULL);
    }
    pBuilder.add("preAddressPostCode",
        pReadOnly.getPresentAddressPostCode() == null ? "" : pReadOnly.getPresentAddressPostCode());
    pBuilder.add("perAddressLine1", pReadOnly.getPermanentAddressLine1());
    pBuilder.add("perAddressLine2",
        pReadOnly.getPermanentAddressLine2() == null ? "" : pReadOnly.getPermanentAddressLine2());
    JsonObjectBuilder permanentCountryBuilder = Json.createObjectBuilder();
    permanentCountryBuilder.add("id", pReadOnly.getPermanentAddressCountryId()).add("name",
        mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName());
    pBuilder.add("perAddressCountry", permanentCountryBuilder);
    if(mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName().equals("Bangladesh")) {
      if(pReadOnly.getPermanentAddressDivisionId() == 0 || pReadOnly.getPermanentAddressDivisionId() == null) {
        pBuilder.add("perAddressDivision", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentDivisionBuilder = Json.createObjectBuilder();
        permanentDivisionBuilder.add("id", pReadOnly.getPermanentAddressDivisionId()).add("name",
            mDivisionManager.get(pReadOnly.getPermanentAddressDivisionId()).getDivisionName());
        pBuilder.add("perAddressDivision", permanentDivisionBuilder);
      }
      if(pReadOnly.getPermanentAddressDistrictId() == 0 || pReadOnly.getPermanentAddressDistrictId() == null) {
        pBuilder.add("perAddressDistrict", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentDistrictBuilder = Json.createObjectBuilder();
        permanentDistrictBuilder.add("id", pReadOnly.getPermanentAddressDistrictId()).add("name",
            mDistrictManager.get(pReadOnly.getPermanentAddressDistrictId()).getDistrictName());
        pBuilder.add("perAddressDistrict", permanentDistrictBuilder);
      }
      if(pReadOnly.getPermanentAddressThanaId() == 0 || pReadOnly.getPermanentAddressThanaId() == null) {
        pBuilder.add("perAddressThana", JsonValue.NULL);
      }
      else {
        JsonObjectBuilder permanentThanaBuilder = Json.createObjectBuilder();
        permanentThanaBuilder.add("id", pReadOnly.getPermanentAddressThanaId()).add("name",
            mThanaManager.get(pReadOnly.getPermanentAddressThanaId()).getThanaName());
        pBuilder.add("perAddressThana", permanentThanaBuilder);
      }
    }
    else {
      pBuilder.add("perAddressDivision", JsonValue.NULL);
      pBuilder.add("perAddressDistrict", JsonValue.NULL);
      pBuilder.add("perAddressThana", JsonValue.NULL);
    }
    pBuilder.add("perAddressPostCode",
        pReadOnly.getPermanentAddressPostCode() == null ? "" : pReadOnly.getPermanentAddressPostCode());
    pBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());
    if(pReadOnly.getEmergencyContactRelationId() == 0 || pReadOnly.getEmergencyContactRelationId() == null) {
      pBuilder.add("emergencyContactRelation", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder relationBuilder = Json.createObjectBuilder();
      relationBuilder.add("id", pReadOnly.getEmergencyContactRelationId()).add("name",
          mRelationType.get(pReadOnly.getEmergencyContactRelationId()).getLabel());
      pBuilder.add("emergencyContactRelation", relationBuilder);
    }
    pBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());
    pBuilder.add("emergencyContactAddress",
        pReadOnly.getEmergencyContactAddress() == null ? "" : pReadOnly.getEmergencyContactAddress());
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(pJsonObject.getString("employeeId"));
    pMutable.setFirstName(pJsonObject.getString("firstName").trim());
    pMutable.setLastName(pJsonObject.getString("lastName").trim());
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

    pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    pMutable.setEmergencyContactRelationId(pJsonObject.getJsonObject("emergencyContactRelation").getInt("id"));
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    pMutable.setEmergencyContactAddress(pJsonObject.containsKey("emergencyContactAddress") ? pJsonObject
        .getString("emergencyContactAddress") : "");
  }
}
