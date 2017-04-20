package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class PersonalInformationBuilder implements Builder<PersonalInformation, MutablePersonalInformation> {

  @Autowired
  UserManager userManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, PersonalInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("firstName", pReadOnly.getFirstName());
    pBuilder.add("lastName", pReadOnly.getLastName());
    pBuilder.add("fatherName", pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("birthday", pReadOnly.getDateOfBirth());
    pBuilder.add("nationality", pReadOnly.getNationality());
    pBuilder.add("religion", pReadOnly.getReligion());
    pBuilder.add("maritalStatus", pReadOnly.getMaritalStatus());
    pBuilder.add("spouseName", pReadOnly.getSpouseName());
    pBuilder.add("nationalIdNo", pReadOnly.getNationalId());
    pBuilder.add("bloodGroup", pReadOnly.getBloodGroup());
    pBuilder.add("spouseNationalIdNo", pReadOnly.getSpouseNationalId());
    pBuilder.add("website", pReadOnly.getWebsite());
    pBuilder.add("organizationalEmail", pReadOnly.getOrganizationalEmail());
    pBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
    pBuilder.add("mobile", pReadOnly.getMobileNumber());
    pBuilder.add("phone", pReadOnly.getPhoneNumber());
    pBuilder.add("presentAddressHouse", pReadOnly.getPresentAddressHouse());
    pBuilder.add("presentAddressRoad", pReadOnly.getPresentAddressRoad());
    pBuilder.add("presentAddressPoliceStation", pReadOnly.getPresentAddressThana());
    pBuilder.add("presentAddressPostalCode", pReadOnly.getPresentAddressZip());
    pBuilder.add("presentAddressDistrict", pReadOnly.getPresentAddressDistrict());
    pBuilder.add("presentAddressDivision", pReadOnly.getPresentAddressDivision());
    pBuilder.add("presentAddressCountry", pReadOnly.getPresentAddressCountry());
    pBuilder.add("permanentAddressHouse", pReadOnly.getPermanentAddressHouse());
    pBuilder.add("permanentAddressRoad", pReadOnly.getPermanentAddressRoad());
    pBuilder.add("permanentAddressPoliceStation", pReadOnly.getPermanentAddressThana());
    pBuilder.add("permanentAddressPostalCode", pReadOnly.getPermanentAddressZip());
    pBuilder.add("permanentAddressDistrict", pReadOnly.getPermanentAddressDistrict());
    pBuilder.add("permanentAddressDivision", pReadOnly.getPermanentAddressDivision());
    pBuilder.add("permanentAddressCountry", pReadOnly.getPermanentAddressCountry());
    pBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName());
    pBuilder.add("emergencyContactRelation", pReadOnly.getEmergencyContactRelation());
    pBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone());
    pBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress());
  }

  @Override
  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(!pJsonObject.getString("firstName").equals("")) {
      pMutable.setFirstName(pJsonObject.getString("firstName"));
    }
    if(!pJsonObject.getString("lastName").equals("")) {
      pMutable.setLastName(pJsonObject.getString("lastName"));
    }
    if(!pJsonObject.getString("fatherName").equals("")) {
      pMutable.setFatherName(pJsonObject.getString("fatherName"));
    }
    if(!pJsonObject.getString("motherName").equals("")) {
      pMutable.setMotherName(pJsonObject.getString("motherName"));
    }
    if(pJsonObject.getJsonObject("gender") != null) {
      pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
    }
    if(!pJsonObject.getString("birthday").equals("")) {
      pMutable.setDateOfBirth(pJsonObject.getString("birthday"));
    }
    if(pJsonObject.getJsonObject("nationality") != null) {
      pMutable.setNationality(pJsonObject.getJsonObject("nationality").getString("name"));
    }
    if(pJsonObject.getJsonObject("religion") != null) {
      pMutable.setReligion(pJsonObject.getJsonObject("religion").getString("name"));
    }
    if(pJsonObject.getJsonObject("maritalStatus") != null) {
      pMutable.setMaritalStatus(pJsonObject.getJsonObject("maritalStatus").getString("name"));
    }
    if(!pJsonObject.getString("spouseName").equals("")) {
      pMutable.setSpouseName(pJsonObject.getString("spouseName"));
    }
    if(!pJsonObject.getString("nationalIdNo").equals("")) {
      pMutable.setNationalId(pJsonObject.getString("nationalIdNo"));
    }
    if(pJsonObject.getJsonObject("bloodGroup") != null) {
      pMutable.setBloodGroup(pJsonObject.getJsonObject("bloodGroup").getString("name"));
    }
    if(!pJsonObject.getString("spouseNationalIdNo").equals("")) {
      pMutable.setSpouseNationalId(pJsonObject.getString("spouseNationalIdNo"));
    }
    if(!pJsonObject.getString("website").equals("")) {
      pMutable.setWebsite(pJsonObject.getString("website"));
    }
    if(!pJsonObject.getString("organizationalEmail").equals("")) {
      pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
    }
    if(!pJsonObject.getString("personalEmail").equals("")) {
      pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
    }
    if(!pJsonObject.getString("mobile").equals("")) {
      pMutable.setMobileNumber(pJsonObject.getString("mobile"));
    }
    if(!pJsonObject.getString("phone").equals("")) {
      pMutable.setPhoneNumber(pJsonObject.getString("phone"));
    }
    if(!pJsonObject.getString("presentAddressHouse").equals("")) {
      pMutable.setPresentAddressHouse(pJsonObject.getString("presentAddressHouse"));
    }
    if(!pJsonObject.getString("presentAddressRoad").equals("")) {
      pMutable.setPresentAddressRoad(pJsonObject.getString("presentAddressRoad"));
    }
    if(pJsonObject.getJsonObject("presentAddressPoliceStation") != null) {
      pMutable.setPresentAddressThana(pJsonObject.getJsonObject("presentAddressPoliceStation").getString("name"));
    }
    if(pJsonObject.getString("presentAddressPostalCode") != null) {
      pMutable.setPresentAddressZip(pJsonObject.getString("presentAddressPostalCode"));
    }
    if(pJsonObject.getJsonObject("presentAddressDistrict") != null) {
      pMutable.setPresentAddressDistrict(pJsonObject.getJsonObject("presentAddressDistrict").getString("name"));
    }
    if(pJsonObject.getJsonObject("presentAddressDivision") != null) {
      pMutable.setPresentAddressDivision(pJsonObject.getJsonObject("presentAddressDivision").getString("name"));
    }
    if(pJsonObject.getJsonObject("presentAddressCountry") != null) {
      pMutable.setPresentAddressCountry(pJsonObject.getJsonObject("presentAddressCountry").getString("name"));
    }
    if(!pJsonObject.getString("permanentAddressHouse").equals("")) {
      pMutable.setPermanentAddressHouse(pJsonObject.getString("permanentAddressHouse"));
    }
    if(!pJsonObject.getString("permanentAddressRoad").equals("")) {
      pMutable.setPermanentAddressRoad(pJsonObject.getString("permanentAddressRoad"));
    }
    if(pJsonObject.getJsonObject("permanentAddressPoliceStation") != null) {
      pMutable.setPermanentAddressThana(pJsonObject.getJsonObject("permanentAddressPoliceStation").getString("name"));
    }
    if(!pJsonObject.getString("permanentAddressPostalCode").equals("")) {
      pMutable.setPermanentAddressZip(pJsonObject.getString("permanentAddressPostalCode"));
    }
    if(pJsonObject.getJsonObject("permanentAddressDistrict") != null) {
      pMutable.setPermanentAddressDistrict(pJsonObject.getJsonObject("permanentAddressDistrict").getString("name"));
    }
    if(pJsonObject.getJsonObject("permanentAddressDivision") != null) {
      pMutable.setPermanentAddressDivision(pJsonObject.getJsonObject("permanentAddressDivision").getString("name"));
    }
    if(pJsonObject.getJsonObject("permanentAddressCountry") != null) {
      pMutable.setPermanentAddressCountry(pJsonObject.getJsonObject("permanentAddressCountry").getString("name"));
    }
    if(!pJsonObject.getString("emergencyContactName").equals("")) {
      pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    }
    if(!pJsonObject.getString("emergencyContactRelation").equals("")) {
      pMutable.setEmergencyContactRelation(pJsonObject.getString("emergencyContactRelation"));
    }
    if(!pJsonObject.getString("emergencyContactPhone").equals("")) {
      pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    }
    if(!pJsonObject.getString("emergencyContactAddress").equals("")) {
      pMutable.setEmergencyContactAddress(pJsonObject.getString("emergencyContactAddress"));
    }
  }
}
