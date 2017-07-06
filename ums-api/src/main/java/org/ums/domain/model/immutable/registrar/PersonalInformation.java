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

  String getNidNo();

  Integer getMaritalStatusId();

  MaritalStatus getMaritalStatus();

  String getSpouseName();

  String getSpouseNidNo();

  String getWebsite();

  String getOrganizationalEmail();

  String getPersonalEmail();

  String getMobileNumber();

  String getPhoneNumber();

  String getPresentAddressLine1();

  String getPresentAddressLine2();

  Integer getPresentAddressCountryId();

  Country getPresentAddressCountry();

  Integer getPresentAddressDivisionId();

  Division getPresentAddressDivision();

  Integer getPresentAddressDistrictId();

  District getPresentAddressDistrict();

  Integer getPresentAddressThanaId();

  Thana getPresentAddressThana();

  String getPresentAddressPostCode();

  String getPermanentAddressLine1();

  String getPermanentAddressLine2();

  Integer getPermanentAddressCountryId();

  Country getPermanentAddressCountry();

  Integer getPermanentAddressDivisionId();

  Division getPermanentAddressDivision();

  Integer getPermanentAddressDistrictId();

  District getPermanentAddressDistrict();

  Integer getPermanentAddressThanaId();

  Thana getPermanentAddressThana();

  String getPermanentAddressPostCode();

  String getEmergencyContactName();

  Integer getEmergencyContactRelationId();

  RelationType getEmergencyContactRelation();

  String getEmergencyContactPhone();

  String getEmergencyContactAddress();
}
