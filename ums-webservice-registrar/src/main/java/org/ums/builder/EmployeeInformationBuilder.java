package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.*;
import org.ums.domain.model.mutable.registrar.*;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeInformationBuilder implements Builder<ServiceInformation, MutableServiceInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("employeeDesignation", pReadOnly.getDesignation());
    pBuilder.add("employeeEmploymentType", pReadOnly.getEmploymentType());
    pBuilder.add("employeeDepartment", pReadOnly.getDeptOffice());
    pBuilder.add("employeeJoiningDate", pReadOnly.getJoiningDate());
    pBuilder.add("employeeContractualDate", pReadOnly.getJobContractualDate());
    pBuilder.add("employeeProbationDate", pReadOnly.getJobProbationDate());
    pBuilder.add("employeeJobPermanentDate", pReadOnly.getJobPermanentDate());
    pBuilder.add("employeeExtensionNumber", pReadOnly.getExtNo());
    pBuilder.add("employeeShortName", pReadOnly.getShortName());
    pBuilder.add("employeeRoomNumber", pReadOnly.getRoomNo());
    pBuilder.add("employeeResignDate", pReadOnly.getJobTerminationDate());
  }

  @Override
  public void build(MutableServiceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setDesignation(pJsonObject.getInt("employeeDesignation"));
    pMutable.setEmploymentType(pJsonObject.getInt("employeeEmploymentType"));
    pMutable.setDeptOffice(pJsonObject.getInt("employeeDepartment"));
    pMutable.setJoiningDate(pJsonObject.getString("employeeJoiningDate"));
    pMutable.setJobContractualDate(pJsonObject.getString("employeeContractualDate"));
    pMutable.setJobProbationDate(pJsonObject.getString("employeeProbationDate"));
    pMutable.setJobPermanentDate(pJsonObject.getString("employeeJobPermanentDate"));
    pMutable.setExtNo(pJsonObject.getInt("employeeExtensionNumber"));
    pMutable.setShortName(pJsonObject.getString("employeeShortName"));
    pMutable.setRoomNo(pJsonObject.getString("employeeRoomNumber"));
    pMutable.setJobTerminationDate(pJsonObject.getString("employeeResignDate"));
  }

  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("academicDegreeName", pReadOnly.getDegreeName());
    pBuilder.add("academicInstitution", pReadOnly.getDegreeInstitute());
    pBuilder.add("academicPassingYear", pReadOnly.getDegreePassingYear());
  }

  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setDegreeName(pJsonObject.getJsonObject("academicDegreeName").getString("name"));
    pMutable.setDegreeInstitute(pJsonObject.getString("academicInstitution"));
    pMutable.setDegreePassingYear(pJsonObject.getString("academicPassingYear"));
  }

  public void build(JsonObjectBuilder pBuilder, AwardInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("awardName", pReadOnly.getAwardName());
    pBuilder.add("awardInstitute", pReadOnly.getAwardInstitute());
    pBuilder.add("awardedYear", pReadOnly.getAwardedYear());
    pBuilder.add("awardShortDescription", pReadOnly.getAwardShortDescription());
  }

  public void build(MutableAwardInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setAwardName(pJsonObject.getString("awardName"));
    pMutable.setAwardInstitute(pJsonObject.getString("awardInstitute"));
    pMutable.setAwardedYear(pJsonObject.getString("awardedYear"));
    pMutable.setAwardShortDescription(pJsonObject.getString("awardShortDescription"));
  }

  public void build(JsonObjectBuilder pBuilder, ExperienceInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("experienceInstitution", pReadOnly.getExperienceInstitute());
    pBuilder.add("experienceDesignation", pReadOnly.getDesignation());
    pBuilder.add("experienceFrom", pReadOnly.getExperienceFromDate());
    pBuilder.add("experienceTo", pReadOnly.getExperienceToDate());
  }

  public void build(MutableExperienceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setExperienceInstitute(pJsonObject.getString("experienceInstitution"));
    pMutable.setDesignation(pJsonObject.getString("experienceDesignation"));
    pMutable.setExperienceFromDate(pJsonObject.getString("experienceFrom"));
    pMutable.setExperienceToDate(pJsonObject.getString("experienceTo"));
  }

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

  public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
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
    pMutable.setBloodGroup(pJsonObject.getString("bloodGroup"));
    pMutable.setSpouseNationalId(pJsonObject.getInt("spouseNationalIdNo"));
    pMutable.setWebsite(pJsonObject.getString("website"));
    pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
    pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
    pMutable.setMobileNumber(pJsonObject.getString("mobile"));
    pMutable.setPhoneNumber(pJsonObject.getString("phone"));
    pMutable.setPresentAddressHouse(pJsonObject.getString("presentAddressHouse"));
    pMutable.setPresentAddressRoad(pJsonObject.getString("presentAddressRoad"));
    pMutable.setPresentAddressThana(pJsonObject.getString("presentAddressPoliceStation"));
    pMutable.setPresentAddressZip(pJsonObject.getString("presentAddressPostalCode"));
    pMutable.setPresentAddressDistrict(pJsonObject.getString("presentAddressDistrict"));
    pMutable.setPresentAddressDivision(pJsonObject.getString("presentAddressDivision"));
    pMutable.setPresentAddressCountry(pJsonObject.getString("presentAddressCountry"));
    pMutable.setPermanentAddressHouse(pJsonObject.getString("permanentAddressHouse"));
    pMutable.setPermanentAddressRoad(pJsonObject.getString("permanentAddressRoad"));
    pMutable.setPermanentAddressThana(pJsonObject.getString("permanentAddressPoliceStation"));
    pMutable.setPermanentAddressZip(pJsonObject.getString("permanentAddressPostalCode"));
    pMutable.setPermanentAddressDistrict(pJsonObject.getString("permanentAddressDistrict"));
    pMutable.setPermanentAddressDivision(pJsonObject.getString("permanentAddressDivision"));
    pMutable.setPermanentAddressCountry(pJsonObject.getString("permanentAddressCountry"));
    pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
    pMutable.setEmergencyContactRelation(pJsonObject.getString("emergencyContactRelation"));
    pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
    pMutable.setEmergencyContactAddress(pJsonObject.getString("emergencyContactAddress"));
  }

  public void build(JsonObjectBuilder pBuilder, PublicationInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employeeIdss", pReadOnly.getEmployeeId());
    pBuilder.add("publicationTitle", pReadOnly.getPublicationTitle());
    pBuilder.add("publicationInterestGenre", pReadOnly.getInterestGenre());
    pBuilder.add("authorsName", pReadOnly.getAuthor());
    pBuilder.add("publisherName", pReadOnly.getPublisherName());
    pBuilder.add("dateOfPublication", pReadOnly.getDateOfPublication());
    pBuilder.add("publicationType", pReadOnly.getPublicationType());
    pBuilder.add("publicationWebLink", pReadOnly.getPublicationWebLink());
  }

  public void build(MutablePublicationInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setPublicationTitle(pJsonObject.getString("publicationTitle"));
    pMutable.setInterestGenre(pJsonObject.getString("publicationInterestGenre"));
    pMutable.setAuthor(pJsonObject.getString("authorsName"));
    pMutable.setPublisherName(pJsonObject.getString("publisherName"));
    pMutable.setDateOfPublication(pJsonObject.getString("dateOfPublication"));
    pMutable.setPublicationType(pJsonObject.getJsonObject("publicationType").getString("name"));
    pMutable.setPublicationWebLink(pJsonObject.getString("publicationWebLink"));
  }

  public void build(JsonObjectBuilder pBuilder, TrainingInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("trainingName", pReadOnly.getTrainingName());
    pBuilder.add("trainingInstitution", pReadOnly.getTrainingInstitute());
    pBuilder.add("trainingFrom", pReadOnly.getTrainingFromDate());
    pBuilder.add("trainingTo", pReadOnly.getTrainingToDate());
  }

  public void build(MutableTrainingInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setTrainingName(pJsonObject.getString("trainingName"));
    pMutable.setTrainingInstitute(pJsonObject.getString("trainingInstitution"));
    pMutable.setTrainingFromDate(pJsonObject.getString("trainingFrom"));
    pMutable.setTrainingToDate(pJsonObject.getString("trainingTo"));
  }
}
