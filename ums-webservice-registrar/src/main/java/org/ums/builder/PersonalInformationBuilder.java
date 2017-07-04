package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.formatter.DateFormat;
import org.ums.manager.common.NationalityManager;
import org.ums.usermanagement.user.UserManager;

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

  @Override
  public void build(JsonObjectBuilder pBuilder, PersonalInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("employeeId", pReadOnly.getId());

    pBuilder.add("firstName", pReadOnly.getFirstName());

    pBuilder.add("lastName", pReadOnly.getLastName());

    pBuilder.add("fatherName", pReadOnly.getFatherName());

    pBuilder.add("motherName", pReadOnly.getMotherName());

    pBuilder.add("gender", pReadOnly.getGender());

    pBuilder.add("dateOfBirth", mDateFormat.format(pReadOnly.getDateOfBirth()));

    pBuilder.add("nationality", pReadOnly.getNationality().getId());

    pBuilder.add("religion", pReadOnly.getReligion().getId());

    pBuilder.add("maritalStatus", pReadOnly.getMaritalStatus().getId());

    if(pReadOnly.getSpouseName() != null) {
      pBuilder.add("spouseName", pReadOnly.getSpouseName());
    }
    else {
      pBuilder.add("spouseName", "");
    }

    if(pReadOnly.getNationalId() != null) {
      pBuilder.add("nationalIdNo", pReadOnly.getNationalId());
    }
    else {
      pBuilder.add("nationalIdNo", "");
    }

    pBuilder.add("bloodGroup", pReadOnly.getBloodGroup().getId());

    if(pReadOnly.getSpouseNationalId() != null) {
      pBuilder.add("spouseNationalIdNo", pReadOnly.getSpouseNationalId());
    }
    else {
      pBuilder.add("spouseNationalIdNo", "");
    }

    if(pReadOnly.getWebsite() != null) {
      pBuilder.add("personalWebsite", pReadOnly.getWebsite());
    }
    else {
      pBuilder.add("personalWebsite", "");
    }

    if(pReadOnly.getOrganizationalEmail() != null) {
      pBuilder.add("organizationalEmail", pReadOnly.getOrganizationalEmail());
    }
    else {
      pBuilder.add("organizationalEmail", "");
    }

    pBuilder.add("personalEmail", pReadOnly.getPersonalEmail());

    pBuilder.add("mobile", pReadOnly.getMobileNumber());

    if(pReadOnly.getPhoneNumber() != null) {
      pBuilder.add("phone", pReadOnly.getPhoneNumber());
    }
    else {
      pBuilder.add("phone", "");
    }

    pBuilder.add("preAddressHouse", pReadOnly.getPresentAddressHouse());

    if(pReadOnly.getPresentAddressRoad() != null) {
      pBuilder.add("preAddressRoad", pReadOnly.getPresentAddressRoad());
    }
    else {
      pBuilder.add("preAddressRoad", "");
    }

    if(pReadOnly.getPresentAddressThana() != null) {
      pBuilder.add("preAddressThana", pReadOnly.getPresentAddressThana().getId());
    }
    else {
      pBuilder.add("preAddressThana", "");
    }

    if(pReadOnly.getPresentAddressZip() != null) {
      pBuilder.add("preAddressPostOfficeNo", pReadOnly.getPresentAddressZip());
    }
    else {
      pBuilder.add("preAddressPostOfficeNo", "");
    }

    if(pReadOnly.getPresentAddressDistrict() != null) {
      pBuilder.add("preAddressDistrict", pReadOnly.getPresentAddressDistrict().getId());
    }
    else {
      pBuilder.add("preAddressDistrict", "");
    }

    if(pReadOnly.getPresentAddressDivision() != null) {
      pBuilder.add("preAddressDivision", pReadOnly.getPresentAddressDivision().getId());
    }
    else {
      pBuilder.add("preAddressDivision", "");
    }

    pBuilder.add("preAddressCountry", pReadOnly.getPresentAddressCountry().getId());

    pBuilder.add("perAddressHouse", pReadOnly.getPermanentAddressHouse());

    if(pReadOnly.getPermanentAddressRoad() != null) {
      pBuilder.add("perAddressRoad", pReadOnly.getPermanentAddressRoad());
    }
    else {
      pBuilder.add("perAddressRoad", "");
    }

    if(pReadOnly.getPresentAddressThana() != null) {
      pBuilder.add("perAddressThana", pReadOnly.getPermanentAddressThana().getId());
    }
    else {
      pBuilder.add("perAddressThana", "");
    }

    if(pReadOnly.getPermanentAddressZip() != null) {
      pBuilder.add("perAddressPostOfficeNo", pReadOnly.getPermanentAddressZip());
    }
    else {
      pBuilder.add("perAddressPostOfficeNo", "");
    }

    if(pReadOnly.getPermanentAddressDistrict() != null) {
      pBuilder.add("perAddressDistrict", pReadOnly.getPermanentAddressDistrict().getId());
    }
    else {
      pBuilder.add("perAddressDistrict", "");
    }

    if(pReadOnly.getPermanentAddressDivision() != null) {
      pBuilder.add("perAddressDivision", pReadOnly.getPermanentAddressDivision().getId());
    }
    else {
      pBuilder.add("perAddressDivision", "");
    }

    pBuilder.add("perAddressCountry", pReadOnly.getPermanentAddressCountry().getId());

    pBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());

    pBuilder.add("emergencyContactRelation", pReadOnly.getEmergencyContactRelation().getId());

    pBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());

    if(pReadOnly.getEmergencyContactAddress() != null) {
      pBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress());
    }
    else {
      pBuilder.add("emergencyContactAddress", "");
    }
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    // pMutable.setId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    // pMutable.setFirstName(pJsonObject.getString("firstName"));
    // pMutable.setLastName(pJsonObject.getString("lastName"));
    // pMutable.setFatherName(pJsonObject.getString("fatherName"));
    // pMutable.setMotherName(pJsonObject.getString("motherName"));
    // pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
    // pMutable.setDateOfBirth(mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
    // pMutable.setNationality(pJsonObject.getJsonObject("nationality").getInt("id"));
    // pMutable.setReligion(pJsonObject.getJsonObject("religion").getString("name"));
    // pMutable.setMaritalStatus(pJsonObject.getJsonObject("maritalStatus").getString("name"));
    // if(!pJsonObject.containsKey("spouseName")) {
    // pMutable.setSpouseName("");
    // }
    // else {
    // pMutable.setSpouseName(pJsonObject.getString("spouseName"));
    // }
    // if(!pJsonObject.containsKey("nationalId")) {
    // pMutable.setNationalId("");
    // }
    // else {
    // pMutable.setNationalId(pJsonObject.getString("nationalIdNo"));
    // }
    // pMutable.setBloodGroup(pJsonObject.getJsonObject("bloodGroup").getString("name"));
    // if(!pJsonObject.containsKey("spouseNationalIdNo")) {
    // pMutable.setSpouseNationalId("");
    // }
    // else {
    // pMutable.setSpouseNationalId(pJsonObject.getString("spouseNationalIdNo"));
    // }
    // if(!pJsonObject.containsKey("personalWebsite")) {
    // pMutable.setWebsite("");
    // }
    // else {
    // pMutable.setWebsite(pJsonObject.getString("personalWebsite"));
    // }
    // if(!pJsonObject.containsKey("organizationalEmail")) {
    // pMutable.setOrganizationalEmail("");
    // }
    // else {
    // pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
    // }
    // pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
    // pMutable.setMobileNumber(pJsonObject.getString("mobile"));
    // if(!pJsonObject.containsKey("phone")) {
    // pMutable.setPhoneNumber("");
    // }
    // else {
    // pMutable.setPhoneNumber(pJsonObject.getString("phone"));
    // }
    // pMutable.setPresentAddressHouse(pJsonObject.getString("preAddressHouse"));
    // if(!pJsonObject.containsKey("preAddressRoad")) {
    // pMutable.setPresentAddressRoad("");
    // }
    // else {
    // pMutable.setPresentAddressRoad(pJsonObject.getString("preAddressRoad"));
    // }
    // if(!pJsonObject.containsKey("preAddressPostOfficeNo")) {
    // pMutable.setPresentAddressZip("");
    // }
    // else {
    // pMutable.setPresentAddressZip(pJsonObject.getString("preAddressPostOfficeNo"));
    // }
    // if(pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
    // pMutable.setPresentAddressThana(pJsonObject.getJsonObject("preAddressThana").getString("name"));
    // pMutable.setPresentAddressDistrict(pJsonObject.getJsonObject("preAddressDistrict").getString("name"));
    // pMutable.setPresentAddressDivision(pJsonObject.getJsonObject("preAddressDivision").getString("name"));
    // }
    // else {
    // pMutable.setPresentAddressThana("");
    // pMutable.setPresentAddressDistrict("");
    // pMutable.setPresentAddressDivision("");
    // }
    // pMutable.setPresentAddressCountry(pJsonObject.getJsonObject("preAddressCountry").getString("name"));
    // pMutable.setPermanentAddressHouse(pJsonObject.getString("perAddressHouse"));
    // if(!pJsonObject.containsKey("perAddressRoad")) {
    // pMutable.setPermanentAddressRoad("");
    // }
    // else {
    // pMutable.setPermanentAddressRoad(pJsonObject.getString("perAddressRoad"));
    // }
    // if(!pJsonObject.containsKey("perAddressPostOfficeNo")) {
    // pMutable.setPermanentAddressZip("");
    // }
    // else {
    // pMutable.setPermanentAddressZip(pJsonObject.getString("perAddressPostOfficeNo"));
    // }
    // if(pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
    // pMutable.setPermanentAddressThana(pJsonObject.getJsonObject("perAddressThana").getString("name"));
    // pMutable.setPermanentAddressDistrict(pJsonObject.getJsonObject("perAddressDistrict").getString("name"));
    // pMutable.setPermanentAddressDivision(pJsonObject.getJsonObject("perAddressDivision").getString("name"));
    // }
    // else {
    // pMutable.setPermanentAddressThana("");
    // pMutable.setPermanentAddressDistrict("");
    // pMutable.setPermanentAddressDivision("");
    // }
    // pMutable.setPermanentAddressCountry(pJsonObject.getJsonObject("perAddressCountry").getString("name"));
    // pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    // pMutable.setEmergencyContactRelation(pJsonObject.getJsonObject("emergencyContactRelation").getString("name"));
    // pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    // if(!pJsonObject.containsKey("emergencyContactAddress")) {
    // pMutable.setEmergencyContactAddress("");
    // }
    // else {
    // pMutable.setEmergencyContactAddress(pJsonObject.getString("emergencyContactAddress"));
    // }
  }
}
