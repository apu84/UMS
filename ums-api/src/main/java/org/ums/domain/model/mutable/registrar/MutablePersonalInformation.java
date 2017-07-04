package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutablePersonalInformation extends PersonalInformation, Editable<String>, MutableIdentifier<String>,
    MutableLastModifier {

  void setFirstName(final String pFirstName);

  void setLastName(final String pLastName);

  void setGender(final String pGender);

  void setBloodGroup(final BloodGroup pBloodGroup);

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setNationality(final Nationality pNationality);

  void setReligion(final Religion pReligion);

  void setDateOfBirth(final Date pDateOfBirth);

  void setNationalId(final Integer pNationalId);

  void setMaritalStatus(final MaritalStatus pMaritalStatus);

  void setSpouseName(final String pSpouseName);

  void setSpouseNationalId(final Integer pSpouseNationalId);

  void setWebsite(final String pWebsite);

  void setOrganizationalEmail(final String pOrganizationEmail);

  void setPersonalEmail(final String pPersonalEmail);

  void setMobileNumber(final Integer pMobileNumber);

  void setPhoneNumber(final Integer pPhoneNumber);

  void setPresentAddressHouse(final String pPresentAddressHouse);

  void setPresentAddressRoad(final String pPresentAddressRoad);

  void setPresentAddressCountry(final Country pPresentAddressHouseCountry);

  void setPresentAddressDivision(final Division pPresentAddressDivision);

  void setPresentAddressDistrict(final District pPresentAddressDistrict);

  void setPresentAddressThana(final Thana pPresentAddressThana);

  void setPresentAddressZip(final Integer pPresentAddressZip);

  void setPermanentAddressHouse(final String pPermanentAddressHouse);

  void setPermanentAddressRoad(final String pPermanentAddressRoad);

  void setPermanentAddressCountry(final Country pPermanentAddressCountry);

  void setPermanentAddressDivision(final Division pPermanentAddressDivision);

  void setPermanentAddressDistrict(final District pPermanentAddressDistrict);

  void setPermanentAddressThana(final Thana pPermanentAddressThana);

  void setPermanentAddressZip(final Integer pPermanentAddressZip);

  void setEmergencyContactName(final String pEmergencyContactName);

  void setEmergencyContactRelation(final RelationType pEmergencyContactRelation);

  void setEmergencyContactPhone(final Integer pEmergencyContactPhone);

  void setEmergencyContactAddress(final String pEmergencyContactAddress);
}
