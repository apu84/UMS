package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;

import java.io.Serializable;
import java.util.Date;

public interface PersonalInformation extends Serializable, EditType<MutablePersonalInformation>, Identifier<String>,
    LastModifier {

  String getFirstName();

  String getLastName();

  String getGender();

  Integer getBloodGroupId();

  BloodGroup getBloodGroup();

  String getFatherName();

  String getMotherName();

  Integer getNationalityId();

  Nationality getNationality();

  Integer getReligionId();

  Religion getReligion();

  Date getDateOfBirth();

  String getNationalId();

  Integer getMaritalStatusId();

  MaritalStatus getMaritalStatus();

  String getSpouseName();

  String getSpouseNationalId();

  String getWebsite();

  String getOrganizationalEmail();

  String getPersonalEmail();

  String getMobileNumber();

  String getPhoneNumber();

  String getPresentAddressHouse();

  String getPresentAddressRoad();

  Integer getPresentAddressCountryId();

  Country getPresentAddressCountry();

  Integer getPresentAddressDivisionId();

  Division getPresentAddressDivision();

  Integer getPresentAddressDistrictId();

  District getPresentAddressDistrict();

  Integer getPresentAddressThanaId();

  Thana getPresentAddressThana();

  Integer getPresentAddressZip();

  String getPermanentAddressHouse();

  String getPermanentAddressRoad();

  Integer getPermanentAddressCountryId();

  Country getPermanentAddressCountry();

  Integer getPermanentAddressDivisionId();

  Division getPermanentAddressDivision();

  Integer getPermanentAddressDistrictId();

  District getPermanentAddressDistrict();

  Integer getPermanentAddressThanaId();

  Thana getPermanentAddressThana();

  Integer getPermanentAddressZip();

  String getEmergencyContactName();

  Integer getEmergencyContactRelationId();

  RelationType getEmergencyContactRelation();

  String getEmergencyContactPhone();

  String getEmergencyContactAddress();
}
