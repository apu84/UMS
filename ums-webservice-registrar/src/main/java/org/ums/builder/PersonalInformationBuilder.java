package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.*;
import org.ums.persistent.model.common.*;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.management.relation.Relation;
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

  @Autowired
  NationalityBuilder mNationalityBuilder;

  @Override
  public void build(JsonObjectBuilder pBuilder, PersonalInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    //
    // PersistentPersonalInformation personalInformation = new PersistentPersonalInformation();
    //
    // pBuilder.add("employeeId", pReadOnly.getId());
    //
    // pBuilder.add("firstName", pReadOnly.getFirstName());
    //
    // pBuilder.add("lastName", pReadOnly.getLastName());
    //
    // pBuilder.add("fatherName", pReadOnly.getFatherName());
    //
    // pBuilder.add("motherName", pReadOnly.getMotherName());
    //
    // pBuilder.add("gender", pReadOnly.getGender());
    //
    // pBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));
    //
    // pBuilder.add("nationality", jsonObjectBuilder);

    // pBuilder.add("religion", (JsonObjectBuilder)
    // mReligionManager.get(personalInformation.getReligionId()));
    //
    // pBuilder.add("maritalStatus",
    // (JsonObjectBuilder) mMaritalStatusManager.get(personalInformation.getMaritalStatusId()));
    //
    // if (pReadOnly.getSpouseName() != null) {
    // pBuilder.add("spouseName", pReadOnly.getSpouseName());
    // } else {
    // pBuilder.add("spouseName", "");
    // }
    //
    // if (pReadOnly.getNidNo() != null) {
    // pBuilder.add("nidNo", pReadOnly.getNidNo());
    // } else {
    // pBuilder.add("nidNo", "");
    // }
    //
    // pBuilder.add("bloodGroup", (JsonObjectBuilder)
    // mBloodGroupManager.get(personalInformation.getBloodGroupId()));
    //
    // if (pReadOnly.getSpouseNidNo() != null) {
    // pBuilder.add("spouseNidNo", pReadOnly.getSpouseNidNo());
    // } else {
    // pBuilder.add("spouseNidNo", "");
    // }
    //
    // if (pReadOnly.getWebsite() != null) {
    // pBuilder.add("website", pReadOnly.getWebsite());
    // } else {
    // pBuilder.add("website", "");
    // }
    //
    // if (pReadOnly.getOrganizationalEmail() != null) {
    // pBuilder.add("organizationalEmail", pReadOnly.getOrganizationalEmail());
    // } else {
    // pBuilder.add("organizationalEmail", "");
    // }
    //
    // pBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
    //
    // pBuilder.add("mobile", pReadOnly.getMobileNumber());
    //
    // if (pReadOnly.getPhoneNumber() != null) {
    // pBuilder.add("phone", pReadOnly.getPhoneNumber());
    // } else {
    // pBuilder.add("phone", "");
    // }
    //
    // pBuilder.add("preAddressLine1", pReadOnly.getPresentAddressLine1());
    //
    // if (pReadOnly.getPresentAddressLine2() != null) {
    // pBuilder.add("preAddressLine2", pReadOnly.getPresentAddressLine2());
    // } else {
    // pBuilder.add("preAddressLine2", "");
    // }
    //
    // if (pReadOnly.getPresentAddressThana() != null) {
    // pBuilder.add("preAddressThana",
    // (JsonObjectBuilder) mThanaManager.get(personalInformation.getPresentAddressThanaId()));
    // } else {
    // pBuilder.add("preAddressThana", "");
    // }
    //
    // if (pReadOnly.getPresentAddressPostCode() != null) {
    // pBuilder.add("preAddressPostCode", pReadOnly.getPresentAddressPostCode());
    // } else {
    // pBuilder.add("preAddressPostCode", "");
    // }
    //
    // if (pReadOnly.getPresentAddressDistrict() != null) {
    // pBuilder.add("preAddressDistrict",
    // (JsonObjectBuilder) mDistrictManager.get(personalInformation.getPresentAddressDistrictId()));
    // } else {
    // pBuilder.add("preAddressDistrict", "");
    // }
    //
    // if (pReadOnly.getPresentAddressDivision() != null) {
    // pBuilder.add("preAddressDivision",
    // (JsonObjectBuilder) mDivisionManager.get(personalInformation.getPresentAddressDivisionId()));
    // } else {
    // pBuilder.add("preAddressDivision", "");
    // }
    //
    // pBuilder.add("preAddressCountry",
    // (JsonObjectBuilder) mCountryManager.get(personalInformation.getPresentAddressCountryId()));
    //
    // pBuilder.add("perAddressLine1", pReadOnly.getPermanentAddressLine1());
    //
    // if (pReadOnly.getPermanentAddressLine2() != null) {
    // pBuilder.add("preAddressLine2", pReadOnly.getPermanentAddressLine2());
    // } else {
    // pBuilder.add("preAddressLine2", "");
    // }
    //
    // if (pReadOnly.getPresentAddressThana() != null) {
    // pBuilder.add("perAddressThana",
    // (JsonObjectBuilder) mThanaManager.get(personalInformation.getPermanentAddressThanaId()));
    // } else {
    // pBuilder.add("perAddressThana", "");
    // }
    //
    // if (pReadOnly.getPermanentAddressPostCode() != null) {
    // pBuilder.add("perAddressPostCode", pReadOnly.getPermanentAddressPostCode());
    // } else {
    // pBuilder.add("perAddressPostCode", "");
    // }
    //
    // if (pReadOnly.getPermanentAddressDistrict() != null) {
    // pBuilder.add("perAddressDistrict",
    // (JsonObjectBuilder)
    // mDistrictManager.get(personalInformation.getPermanentAddressDistrictId()));
    // } else {
    // pBuilder.add("perAddressDistrict", "");
    // }
    //
    // if (pReadOnly.getPermanentAddressDivision() != null) {
    // pBuilder.add("perAddressDivision",
    // (JsonObjectBuilder)
    // mDivisionManager.get(personalInformation.getPermanentAddressDivisionId()));
    // } else {
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
    // if (pReadOnly.getEmergencyContactAddress() != null) {
    // pBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress());
    // } else {
    // pBuilder.add("emergencyContactAddress", "");
    // }
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setFirstName(pJsonObject.getString("firstName"));
    pMutable.setLastName(pJsonObject.getString("lastName"));
    pMutable.setFatherName(pJsonObject.getString("fatherName"));
    pMutable.setMotherName(pJsonObject.getString("motherName"));
    pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
    pMutable.setDateOfBirth(mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
    Nationality nationality = mNationalityManager.get(pJsonObject.getJsonObject("nationality").getInt("id"));
    pMutable.setNationality(nationality);
    Religion religion = mReligionManager.get(pJsonObject.getJsonObject("religion").getInt("id"));
    pMutable.setReligion(religion);
    MaritalStatus maritalStatus = mMaritalStatusManager.get(pJsonObject.getJsonObject("maritalStatus").getInt("id"));
    pMutable.setMaritalStatus(maritalStatus);
    pMutable.setSpouseName(pJsonObject.containsKey("spouseName") ? pJsonObject.getString("spouseName") : "");
    pMutable.setNidNo(pJsonObject.containsKey("nationalId") ? pJsonObject.getString("nidNo") : "");
    BloodGroup bloodGroup = mBloodGroupManager.get(pJsonObject.getJsonObject("bloodGroup").getInt("id"));
    pMutable.setBloodGroup(bloodGroup);
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
    Country presentCountry = mCountryManager.get(pJsonObject.getJsonObject("preAddressCountry").getInt("id"));
    pMutable.setPresentAddressCountry(presentCountry);
    if(pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
      Division presentDivision = mDivisionManager.get(pJsonObject.getJsonObject("preAddressDivision").getInt("id"));
      District presentDistrict = mDistrictManager.get(pJsonObject.getJsonObject("preAddressDistrict").getInt("id"));
      Thana presentThana = mThanaManager.get(pJsonObject.getJsonObject("preAddressThana").getInt("id"));
      pMutable.setPresentAddressThana(presentThana);
      pMutable.setPresentAddressDistrict(presentDistrict);
      pMutable.setPresentAddressDivision(presentDivision);
      pMutable.setPresentAddressPostCode(pJsonObject.containsKey("preAddressPostCode") ? pJsonObject
          .getString("preAddressPostCode") : "");
    }
    else {
      pMutable.setPresentAddressThana(null);
      pMutable.setPresentAddressDistrict(null);
      pMutable.setPresentAddressDivision(null);
      pMutable.setPresentAddressPostCode("");
    }
    pMutable.setPermanentAddressLine1(pJsonObject.getString("perAddressLine1"));
    pMutable.setPermanentAddressLine2(pJsonObject.containsKey("preAddressLine2") ? pJsonObject
        .getString("preAddressLine2") : "");
    Country permanentCountry = mCountryManager.get(pJsonObject.getJsonObject("perAddressCountry").getInt("id"));
    pMutable.setPermanentAddressCountry(permanentCountry);
    if(pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
      Division permanentDivision = mDivisionManager.get(pJsonObject.getJsonObject("perAddressDivision").getInt("id"));
      District permanentDistrict = mDistrictManager.get(pJsonObject.getJsonObject("perAddressDistrict").getInt("id"));
      Thana permanentThana = mThanaManager.get(pJsonObject.getJsonObject("perAddressThana").getInt("id"));
      pMutable.setPermanentAddressThana(permanentThana);
      pMutable.setPermanentAddressDistrict(permanentDistrict);
      pMutable.setPermanentAddressDivision(permanentDivision);
      pMutable.setPermanentAddressPostCode(pJsonObject.containsKey("perAddressPostCode") ? pJsonObject
          .getString("perAddressPostCode") : "");
    }
    else {
      pMutable.setPermanentAddressThana(null);
      pMutable.setPermanentAddressDistrict(null);
      pMutable.setPermanentAddressDivision(null);
      pMutable.setPermanentAddressPostCode("");
    }

    pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    RelationType relationType =
        mRelationTypeManager.get(pJsonObject.getJsonObject("emergencyContactRelation").getInt("id"));

    pMutable.setEmergencyContactRelation(relationType);
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    pMutable.setEmergencyContactAddress(pJsonObject.containsKey("emergencyContactAddress") ? pJsonObject
        .getString("emergencyContactAddress") : "");
  }
}
