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
        generalObjectBuilder.add("salutation", Json.createObjectBuilder().add("id", pReadOnly.getSalutationId()).add("name",
                Salutation.get(pReadOnly.getSalutationId()).getLabel()));
        generalObjectBuilder.add("name", pReadOnly.getName());
        generalObjectBuilder.add("fatherName", pReadOnly.getFatherName() == null ? "" : pReadOnly.getFatherName());
        generalObjectBuilder.add("motherName", pReadOnly.getMotherName() == null ? "" : pReadOnly.getMotherName());
        if (pReadOnly.getGender() == null) {
            generalObjectBuilder.add("gender", JsonValue.NULL);
        } else {
            generalObjectBuilder.add("gender", pReadOnly.getGender().equals("M") ? Json.createObjectBuilder().add("id", pReadOnly.getGender()).add("name", "Male") :
                    Json.createObjectBuilder().add("id", pReadOnly.getGender()).add("name", "Female"));
        }
        generalObjectBuilder.add("dateOfBirth", pReadOnly.getDateOfBirth() == null ? "" : mDateFormat.format(pReadOnly.getDateOfBirth()));
        if (pReadOnly.getNationalityId() == 0 || pReadOnly.getNationalityId() == null) {
            generalObjectBuilder.add("nationality", JsonValue.NULL);
        } else {
            generalObjectBuilder.add("nationality", Json.createObjectBuilder().add("id", pReadOnly.getNationalityId()).add("name",
                    NationalityType.get(pReadOnly.getNationalityId()).getLabel()));
        }
        if (pReadOnly.getReligionId() == 0 || pReadOnly.getReligionId() == null) {
            generalObjectBuilder.add("religion", JsonValue.NULL);
        } else {
            generalObjectBuilder.add("religion", Json.createObjectBuilder().add("id", pReadOnly.getReligionId()).add("name",
                    ReligionType.get(pReadOnly.getReligionId()).getLabel()));
        }
        if (pReadOnly.getMaritalStatusId() == 0 || pReadOnly.getMaritalStatusId() == null) {
            generalObjectBuilder.add("maritalStatus", JsonValue.NULL);
        } else {
            generalObjectBuilder.add("maritalStatus", Json.createObjectBuilder().add("id", pReadOnly.getMaritalStatusId()).add("name",
                    MaritalStatusType.get(pReadOnly.getMaritalStatusId()).getLabel()));
        }
        generalObjectBuilder.add("spouseName", pReadOnly.getSpouseName() == null ? "" : pReadOnly.getSpouseName());
        generalObjectBuilder.add("nidNo", pReadOnly.getNidNo() == null ? "" : pReadOnly.getNidNo());
        if (pReadOnly.getBloodGroupId() == 0 || pReadOnly.getBloodGroupId() == null) {
            generalObjectBuilder.add("bloodGroup", JsonValue.NULL);
        } else {
            generalObjectBuilder.add("bloodGroup", Json.createObjectBuilder().add("id", pReadOnly.getBloodGroupId()).add("name",
                    BloodGroupType.get(pReadOnly.getBloodGroupId()).getLabel()));
        }
        generalObjectBuilder.add("spouseNidNo", pReadOnly.getSpouseNidNo() == null ? "" : pReadOnly.getSpouseNidNo());
        generalObjectBuilder.add("website", pReadOnly.getWebsite() == null ? "" : pReadOnly.getWebsite());
        pBuilder.add("general", generalObjectBuilder);

        JsonObjectBuilder contactObjectBuilder = Json.createObjectBuilder();
        contactObjectBuilder.add("organizationalEmail",
                pReadOnly.getOrganizationalEmail() == null ? "" : pReadOnly.getOrganizationalEmail());
        contactObjectBuilder.add("personalEmail", pReadOnly.getPersonalEmail());
        contactObjectBuilder.add("mobile", pReadOnly.getMobileNumber() == null ? "" : pReadOnly.getMobileNumber());
        contactObjectBuilder.add("phone", pReadOnly.getPhoneNumber() == null ? "" : pReadOnly.getPhoneNumber());
        contactObjectBuilder.add("preAddressLine1", pReadOnly.getPresentAddressLine1() == null ? "" : pReadOnly.getPresentAddressLine1());
        if (pReadOnly.getPresentAddressCountryId() == 0 || pReadOnly.getPresentAddressCountryId() == null) {
            contactObjectBuilder.add("preAddressCountry", JsonValue.NULL);
        } else {
            contactObjectBuilder.add("preAddressCountry", Json.createObjectBuilder().add("id", pReadOnly.getPresentAddressCountryId()).add("name",
                    mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName()));
        }
        if ((pReadOnly.getPresentAddressCountryId() != 0 && pReadOnly.getPresentAddressCountryId() != null)) {
            if(mCountryManager.get(pReadOnly.getPresentAddressCountryId()).getName().equals("Bangladesh")) {
                if (pReadOnly.getPresentAddressDivisionId() == 0 || pReadOnly.getPresentAddressDivisionId() == null) {
                    contactObjectBuilder.add("preAddressDivision", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("preAddressDivision", Json.createObjectBuilder().add("id", pReadOnly.getPresentAddressDivisionId()).add("name",
                            mDivisionManager.get(pReadOnly.getPresentAddressDivisionId()).getDivisionName()));
                }
                if (pReadOnly.getPresentAddressDistrictId() == 0 || pReadOnly.getPresentAddressDistrictId() == null) {
                    contactObjectBuilder.add("preAddressDistrict", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("preAddressDistrict", Json.createObjectBuilder().add("id", pReadOnly.getPresentAddressDistrictId()).add("name",
                            mDistrictManager.get(pReadOnly.getPresentAddressDistrictId()).getDistrictName()));
                }
                if (pReadOnly.getPresentAddressThanaId() == 0 || pReadOnly.getPresentAddressThanaId() == null) {
                    contactObjectBuilder.add("preAddressThana", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("preAddressThana", Json.createObjectBuilder().add("id", pReadOnly.getPresentAddressThanaId()).add("name",
                            mThanaManager.get(pReadOnly.getPresentAddressThanaId()).getThanaName()));
                }
            }
            else {
                contactObjectBuilder.add("preAddressDivision", JsonValue.NULL);
                contactObjectBuilder.add("preAddressDistrict", JsonValue.NULL);
                contactObjectBuilder.add("preAddressThana", JsonValue.NULL);
            }
        } else {
            contactObjectBuilder.add("preAddressDivision", JsonValue.NULL);
            contactObjectBuilder.add("preAddressDistrict", JsonValue.NULL);
            contactObjectBuilder.add("preAddressThana", JsonValue.NULL);
        }
        contactObjectBuilder.add("preAddressPostCode",
                pReadOnly.getPresentAddressPostCode() == null ? "" : pReadOnly.getPresentAddressPostCode());
        contactObjectBuilder.add("perAddressLine1", pReadOnly.getPermanentAddressLine1() == null ? "" :
                pReadOnly.getPermanentAddressLine1());
        if (pReadOnly.getPermanentAddressCountryId() == 0 || pReadOnly.getPermanentAddressCountryId() == null) {
            contactObjectBuilder.add("perAddressCountry", JsonValue.NULL);
        } else {
            contactObjectBuilder.add("perAddressCountry", Json.createObjectBuilder().add("id", pReadOnly.getPermanentAddressCountryId()).add("name",
                    mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName()));
        }
        if ((pReadOnly.getPermanentAddressCountryId() != 0 && pReadOnly.getPermanentAddressCountryId() != null)) {
            if(mCountryManager.get(pReadOnly.getPermanentAddressCountryId()).getName().equals("Bangladesh")) {
                if (pReadOnly.getPermanentAddressDivisionId() == 0 || pReadOnly.getPermanentAddressDivisionId() == null) {
                    contactObjectBuilder.add("perAddressDivision", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("perAddressDivision", Json.createObjectBuilder().add("id", pReadOnly.getPermanentAddressDivisionId()).add("name",
                            mDivisionManager.get(pReadOnly.getPermanentAddressDivisionId()).getDivisionName()));
                }
                if (pReadOnly.getPermanentAddressDistrictId() == 0 || pReadOnly.getPermanentAddressDistrictId() == null) {
                    contactObjectBuilder.add("perAddressDistrict", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("perAddressDistrict", Json.createObjectBuilder().add("id", pReadOnly.getPermanentAddressDistrictId()).add("name",
                            mDistrictManager.get(pReadOnly.getPermanentAddressDistrictId()).getDistrictName()));
                }
                if (pReadOnly.getPermanentAddressThanaId() == 0 || pReadOnly.getPermanentAddressThanaId() == null) {
                    contactObjectBuilder.add("perAddressThana", JsonValue.NULL);
                } else {
                    contactObjectBuilder.add("perAddressThana", Json.createObjectBuilder().add("id", pReadOnly.getPermanentAddressThanaId()).add("name",
                            mThanaManager.get(pReadOnly.getPermanentAddressThanaId()).getThanaName()));
                }
            } else {
                contactObjectBuilder.add("perAddressDivision", JsonValue.NULL);
                contactObjectBuilder.add("perAddressDistrict", JsonValue.NULL);
                contactObjectBuilder.add("perAddressThana", JsonValue.NULL);
            }
        } else {
            contactObjectBuilder.add("perAddressDivision", JsonValue.NULL);
            contactObjectBuilder.add("perAddressDistrict", JsonValue.NULL);
            contactObjectBuilder.add("perAddressThana", JsonValue.NULL);
        }
        contactObjectBuilder.add("perAddressPostCode",
                pReadOnly.getPermanentAddressPostCode() == null ? "" : pReadOnly.getPermanentAddressPostCode());
        pBuilder.add("contact", contactObjectBuilder);

        JsonObjectBuilder emergencyContactObjectBuilder = Json.createObjectBuilder();
        emergencyContactObjectBuilder.add("emergencyContactName", pReadOnly.getEmergencyContactName() == null ? "" : pReadOnly.getEmergencyContactName());
        if (pReadOnly.getEmergencyContactRelationId() == 0 || pReadOnly.getEmergencyContactRelationId() == null) {
            emergencyContactObjectBuilder.add("emergencyContactRelation", JsonValue.NULL);
        } else {
            emergencyContactObjectBuilder.add("emergencyContactRelation", Json.createObjectBuilder().add("id", pReadOnly.getEmergencyContactRelationId()).add("name",
                    RelationType.get(pReadOnly.getEmergencyContactRelationId()).getLabel()));
        }
        emergencyContactObjectBuilder.add("emergencyContactPhone", pReadOnly.getEmergencyContactPhone() == null ? "" : pReadOnly.getEmergencyContactPhone());
        emergencyContactObjectBuilder.add("emergencyContactAddress", pReadOnly.getEmergencyContactAddress() == null ? ""
                : pReadOnly.getEmergencyContactAddress());
        pBuilder.add("emergencyContact", emergencyContactObjectBuilder);
    }

    @Override
    public void build(MutablePersonalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
        PersonalInformation personalInformation = mPersonalInformationManager.get(pJsonObject.getString("employeeId"));

        if (pJsonObject.getString("type").equals("general")) {
            pMutable.setId(pJsonObject.getString("employeeId"));
            pMutable.setSalutationId(pJsonObject.getJsonObject("salutation").getInt("id"));
            pMutable.setName(pJsonObject.getString("name").trim());
            pMutable.setFatherName(pJsonObject.getString("fatherName"));
            pMutable.setMotherName(pJsonObject.getString("motherName"));
            pMutable.setGender(pJsonObject.getJsonObject("gender").getString("id"));
            pMutable.setDateOfBirth(pJsonObject.getString("dateOfBirth").isEmpty()
                    ? null : mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
            pMutable.setNationalityId(pJsonObject.get("nationality").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("nationality").getInt("id"));
            pMutable.setReligionId(pJsonObject.get("religion").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("religion").getInt("id"));
            pMutable.setMaritalStatusId(pJsonObject.get("maritalStatus").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("maritalStatus").getInt("id"));
            pMutable.setSpouseName(pJsonObject.containsKey("spouseName") ? pJsonObject.getString("spouseName") : "");
            pMutable.setNidNo(pJsonObject.containsKey("nidNo") ? pJsonObject.getString("nidNo") : "");
            pMutable.setBloodGroupId(pJsonObject.get("bloodGroup").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("bloodGroup").getInt("id"));
            pMutable.setSpouseNidNo(pJsonObject.containsKey("spouseNidNo") ? pJsonObject.getString("spouseNidNo") : "");
            pMutable.setWebsite(pJsonObject.containsKey("website") ? pJsonObject.getString("website") : "");
        } else {
            pMutable.setId(personalInformation.getId());
            pMutable.setSalutationId(personalInformation.getSalutationId());
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

        if (pJsonObject.getString("type").equals("contact")) {
            pMutable.setOrganizationalEmail(pJsonObject.getString("organizationalEmail"));
            pMutable.setPersonalEmail(pJsonObject.getString("personalEmail"));
            pMutable.setMobileNumber(pJsonObject.getString("mobile"));
            pMutable.setPhoneNumber(pJsonObject.getString("phone"));
            pMutable.setPresentAddressLine1(pJsonObject.getString("preAddressLine1"));
            pMutable.setPresentAddressCountryId(pJsonObject.get("preAddressCountry").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("preAddressCountry").getInt("id"));
            if (!pJsonObject.get("preAddressCountry").equals(JsonValue.NULL) && pJsonObject.getJsonObject("preAddressCountry").getString("name").equals("Bangladesh")) {
                pMutable.setPresentAddressDivisionId(pJsonObject.get("preAddressDivision").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("preAddressDivision").getInt("id"));
                pMutable.setPresentAddressDistrictId(pJsonObject.get("preAddressDistrict").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("preAddressDistrict").getInt("id"));
                pMutable.setPresentAddressThanaId(pJsonObject.get("preAddressThana").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("preAddressThana").getInt("id"));
                pMutable.setPresentAddressPostCode(pJsonObject.containsKey("preAddressPostCode") ? pJsonObject
                        .getString("preAddressPostCode") : "");
            } else {
                pMutable.setPresentAddressDivision(null);
                pMutable.setPresentAddressDistrict(null);
                pMutable.setPresentAddressThana(null);
                pMutable.setPresentAddressPostCode("");
            }
            pMutable.setPermanentAddressLine1(pJsonObject.getString("perAddressLine1"));
            pMutable.setPermanentAddressCountryId(pJsonObject.get("perAddressCountry").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("perAddressCountry").getInt("id"));
            if (!pJsonObject.get("perAddressCountry").equals(JsonValue.NULL) && pJsonObject.getJsonObject("perAddressCountry").getString("name").equals("Bangladesh")) {
                pMutable.setPermanentAddressDivisionId(pJsonObject.get("perAddressDivision").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("perAddressDivision").getInt("id"));
                pMutable.setPermanentAddressDistrictId(pJsonObject.get("perAddressDistrict").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("perAddressDistrict").getInt("id"));
                pMutable.setPermanentAddressThanaId(pJsonObject.get("perAddressThana").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("perAddressThana").getInt("id"));
                pMutable.setPermanentAddressPostCode(pJsonObject.containsKey("perAddressPostCode") ? pJsonObject
                        .getString("perAddressPostCode") : "");
            } else {
                pMutable.setPermanentAddressDivision(null);
                pMutable.setPermanentAddressDistrict(null);
                pMutable.setPermanentAddressThana(null);
                pMutable.setPermanentAddressPostCode("");
            }
        } else {
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
        if (pJsonObject.getString("type").equals("emergencyContact")) {
            pMutable.setEmergencyContactName(pJsonObject.getString("emergencyContactName"));
            pMutable.setEmergencyContactRelationId(pJsonObject.get("emergencyContactRelation").equals(JsonValue.NULL) ? null : pJsonObject.getJsonObject("emergencyContactRelation").getInt("id"));
            pMutable.setEmergencyContactPhone(pJsonObject.getString("emergencyContactPhone"));
            pMutable.setEmergencyContactAddress(pJsonObject.containsKey("emergencyContactAddress") ? pJsonObject
                    .getString("emergencyContactAddress") : "");
        } else {
            pMutable.setEmergencyContactName(personalInformation.getEmergencyContactName());
            pMutable.setEmergencyContactRelationId(personalInformation.getEmergencyContactRelationId());
            pMutable.setEmergencyContactPhone(personalInformation.getEmergencyContactPhone());
            pMutable.setEmergencyContactAddress(personalInformation.getEmergencyContactAddress());
        }
    }
}
