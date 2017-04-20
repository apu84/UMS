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
    pMutable.setFirstName(pJsonObject.getString("firstName"));
    pMutable.setLastName(pJsonObject.getString("lastName"));
    pMutable.setFatherName(pJsonObject.getString("fatherName"));
    pMutable.setMotherName(pJsonObject.getString("motherName"));
    System.out.println(pJsonObject.getJsonObject("gender").getString("name"));
    pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
    pMutable.setDateOfBirth(pJsonObject.getString("birthday"));
    pMutable.setNationality(pJsonObject.getJsonObject("nationality").getString("name"));
    pMutable.setReligion(pJsonObject.getJsonObject("religion").getString("name"));
    pMutable.setMaritalStatus(pJsonObject.getJsonObject("maritalStatus").getInt("id"));
    pMutable.setSpouseName(pJsonObject.getString("spouseName"));
    pMutable.setNationalId(pJsonObject.getInt("nationalIdNo"));
    pMutable.setBloodGroup(pJsonObject.getJsonObject("bloodGroup").getString("name"));
    pMutable.setSpouseNationalId(pJsonObject.getInt("spouseNationalIdNo"));
    pMutable.setWebsite(pJsonObject.getString("website"));
    pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
    pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
    pMutable.setMobileNumber(pJsonObject.getString("mobile"));
    pMutable.setPhoneNumber(pJsonObject.getString("phone"));
    pMutable.setPresentAddressHouse(pJsonObject.getString("presentAddressHouse"));
    pMutable.setPresentAddressRoad(pJsonObject.getString("presentAddressRoad"));
    pMutable.setPresentAddressThana(pJsonObject.getJsonObject("presentAddressPoliceStation").getString("name"));
    pMutable.setPresentAddressZip(pJsonObject.getString("presentAddressPostalCode"));
    pMutable.setPresentAddressDistrict(pJsonObject.getJsonObject("presentAddressDistrict").getString("name"));
    pMutable.setPresentAddressDivision(pJsonObject.getJsonObject("presentAddressDivision").getString("name"));
    pMutable.setPresentAddressCountry(pJsonObject.getJsonObject("presentAddressCountry").getString("name"));
    pMutable.setPermanentAddressHouse(pJsonObject.getString("permanentAddressHouse"));
    pMutable.setPermanentAddressRoad(pJsonObject.getString("permanentAddressRoad"));
    pMutable.setPermanentAddressThana(pJsonObject.getJsonObject("permanentAddressPoliceStation").getString("name"));
    pMutable.setPermanentAddressZip(pJsonObject.getString("permanentAddressPostalCode"));
    pMutable.setPermanentAddressDistrict(pJsonObject.getJsonObject("permanentAddressDistrict").getString("name"));
    pMutable.setPermanentAddressDivision(pJsonObject.getJsonObject("permanentAddressDivision").getString("name"));
    pMutable.setPermanentAddressCountry(pJsonObject.getJsonObject("permanentAddressCountry").getString("name"));
    pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    pMutable.setEmergencyContactRelation(pJsonObject.getString("emergencyContactRelation"));
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    pMutable.setEmergencyContactAddress(pJsonObject.getString("emergencyContactAddress"));
  }
}
