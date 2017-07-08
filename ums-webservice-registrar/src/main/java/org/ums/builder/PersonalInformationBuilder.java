package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.enums.common.*;
import org.ums.enums.common.RelationType;
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
  private UserManager userManager;

  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  private CountryManager mCountryManager;

  @Autowired
  private DivisionManager mDivisionManager;

  @Autowired
  private DistrictManager mDistrictManager;

  @Autowired
  private ThanaManager mThanaManager;

  @Autowired
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
    pBuilder.add("genderId", pReadOnly.getGender());
    pBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));
    pBuilder.add("nationalityId", pReadOnly.getNationalityId());
    pBuilder.add("religionId", pReadOnly.getReligionId());
    pBuilder.add("maritalStatusId", pReadOnly.getMaritalStatusId());
    pBuilder.add("spouseName", pReadOnly.getSpouseName() == null ? "" : pReadOnly.getSpouseName());
    pBuilder.add("nidNo", pReadOnly.getNidNo() == null ? "" : pReadOnly.getNidNo());
    pBuilder.add("bloodGroupId", pReadOnly.getBloodGroupId());
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
    pBuilder.add("preAddressCountryId", pReadOnly.getPresentAddressCountryId());
    pBuilder.add("preAddressDivisionId", pReadOnly.getPresentAddressDivisionId());
    pBuilder.add("preAddressDistrictId", pReadOnly.getPresentAddressDistrictId());
    pBuilder.add("preAddressThanaId", pReadOnly.getPresentAddressThanaId());
    pBuilder.add("preAddressPostCode",
        pReadOnly.getPresentAddressPostCode() == null ? "" : pReadOnly.getPresentAddressPostCode());
    pBuilder.add("perAddressLine1", pReadOnly.getPermanentAddressLine1());
    pBuilder.add("perAddressLine2",
        pReadOnly.getPermanentAddressLine2() == null ? "" : pReadOnly.getPermanentAddressLine2());
    pBuilder.add("perAddressCountryId", pReadOnly.getPermanentAddressCountryId());
    pBuilder.add("perAddressDivisionId", pReadOnly.getPermanentAddressDivisionId());
    pBuilder.add("perAddressDistrictId", pReadOnly.getPermanentAddressDistrictId());
    pBuilder.add("perAddressThanaId", pReadOnly.getPermanentAddressThanaId());
    pBuilder.add("perAddressPostCode",
        pReadOnly.getPermanentAddressPostCode() == null ? "" : pReadOnly.getPermanentAddressPostCode());
    pBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());
    pBuilder.add("emergencyContactRelationId", pReadOnly.getEmergencyContactRelationId());
    pBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());
    pBuilder.add("emergencyContactAddress",
        pReadOnly.getEmergencyContactAddress() == null ? "" : pReadOnly.getEmergencyContactAddress());
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
    pMutable.setNationality(mNationalityType.get(pJsonObject.getJsonObject("nationality").getInt("id")));
    pMutable.setReligion(mReligionType.get(pJsonObject.getJsonObject("religion").getInt("id")));
    pMutable.setMaritalStatus(mMaritalStatusType.get(pJsonObject.getJsonObject("maritalStatus").getInt("id")));
    pMutable.setSpouseName(pJsonObject.containsKey("spouseName") ? pJsonObject.getString("spouseName") : "");
    pMutable.setNidNo(pJsonObject.containsKey("nationalId") ? pJsonObject.getString("nidNo") : "");
    pMutable.setBloodGroup(mBloodGroupType.get(pJsonObject.getJsonObject("bloodGroup").getInt("id")));
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
    pMutable.setPresentAddressCountry(mCountryManager.get(pJsonObject.getJsonObject("preAddressCountry").getInt("id")));
    if(pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
      pMutable.setPresentAddressDivision(mDivisionManager.get(pJsonObject.getJsonObject("preAddressDivision").getInt(
          "id")));
      pMutable.setPresentAddressDistrict(mDistrictManager.get(pJsonObject.getJsonObject("preAddressDistrict").getInt(
          "id")));
      pMutable.setPresentAddressThana(mThanaManager.get(pJsonObject.getJsonObject("preAddressThana").getInt("id")));
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
    pMutable.setPermanentAddressCountry(mCountryManager
        .get(pJsonObject.getJsonObject("perAddressCountry").getInt("id")));
    if(pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
      pMutable.setPermanentAddressDivision(mDivisionManager.get(pJsonObject.getJsonObject("perAddressDivision").getInt(
          "id")));
      pMutable.setPermanentAddressDistrict(mDistrictManager.get(pJsonObject.getJsonObject("perAddressDistrict").getInt(
          "id")));
      pMutable.setPermanentAddressThana(mThanaManager.get(pJsonObject.getJsonObject("perAddressThana").getInt("id")));
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
    pMutable.setEmergencyContactRelation(mRelationType.get(pJsonObject.getJsonObject("emergencyContactRelation")
        .getInt("id")));
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    pMutable.setEmergencyContactAddress(pJsonObject.containsKey("emergencyContactAddress") ? pJsonObject
        .getString("emergencyContactAddress") : "");
  }
}
