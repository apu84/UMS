package org.ums.employee.personal;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.*;
import org.ums.enums.common.RelationType;

import java.util.Date;

public interface MutablePersonalInformation extends PersonalInformation, Editable<String>, MutableIdentifier<String>,
    MutableLastModifier {

  void setName(final String pName);

  void setGender(final String pGender);

  void setBloodGroupId(final Integer pBloodGroupId);

  void setBloodGroup(final BloodGroupType pBloodGroup);

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setNationalityId(final Integer pNationalityId);

  void setNationality(final NationalityType pNationality);

  void setReligionId(final Integer pReligionId);

  void setReligion(final ReligionType pReligion);

  void setDateOfBirth(final Date pDateOfBirth);

  void setNidNo(final String pNidNo);

  void setMaritalStatusId(final Integer pMaritalStatusId);

  void setMaritalStatus(final MaritalStatusType pMaritalStatus);

  void setSpouseName(final String pSpouseName);

  void setSpouseNidNo(final String pSpouseNidNo);

  void setWebsite(final String pWebsite);

  void setOrganizationalEmail(final String pOrganizationEmail);

  void setPersonalEmail(final String pPersonalEmail);

  void setMobileNumber(final String pMobileNumber);

  void setPhoneNumber(final String pPhoneNumber);

  void setPresentAddressLine1(final String pPresentAddressLine1);

  void setPresentAddressLine2(final String pPresentAddressLine2);

  void setPresentAddressCountryId(final Integer pPresentAddressCountryId);

  void setPresentAddressCountry(final Country pPresentAddressHouseCountry);

  void setPresentAddressDivisionId(final Integer pPresentAddressDivisionId);

  void setPresentAddressDivision(final Division pPresentAddressDivision);

  void setPresentAddressDistrictId(final Integer pPresentAddressDistrictId);

  void setPresentAddressDistrict(final District pPresentAddressDistrict);

  void setPresentAddressThanaId(final Integer pPresentAddressThanaId);

  void setPresentAddressThana(final Thana pPresentAddressThana);

  void setPresentAddressPostCode(final String pPresentAddressPostCode);

  void setPermanentAddressLine1(final String pPermanentAddressLine1);

  void setPermanentAddressLine2(final String pPermanentAddressLine2);

  void setPermanentAddressCountryId(final Integer pPermanentAddressCountryId);

  void setPermanentAddressCountry(final Country pPermanentAddressCountry);

  void setPermanentAddressDivisionId(final Integer pPermanentAddressDivisionId);

  void setPermanentAddressDivision(final Division pPermanentAddressDivision);

  void setPermanentAddressDistrictId(final Integer pPermanentAddressDistrictId);

  void setPermanentAddressDistrict(final District pPermanentAddressDistrict);

  void setPermanentAddressThanaId(final Integer pPermanentAddressThanaId);

  void setPermanentAddressThana(final Thana pPermanentAddressThana);

  void setPermanentAddressPostCode(final String pPermanentAddressPostCode);

  void setEmergencyContactName(final String pEmergencyContactName);

  void setEmergencyContactRelationId(final Integer pEmergencyContactRelationId);

  void setEmergencyContactRelation(final RelationType pEmergencyContactRelation);

  void setEmergencyContactPhone(final String pEmergencyContactPhone);

  void setEmergencyContactAddress(final String pEmergencyContactAddress);
}
