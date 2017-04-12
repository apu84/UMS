package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.PersonalInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentPersonalInformationDao extends PersonalInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_PERSONAL_INFO (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, NATIONALITY, RELIGION, DATE_OF_BIRTH, NATIONAL_ID_CARD, MARITAL_STATUS, SPOUSE_NAME, SPOUSE_NATIONAL_ID_CARD, WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_HOUSE, PRE_HOUSE_ROAD, PRE_ADD_THANA, PRE_ADD_DISTRICT, PRE_ADD_ZIP, PRE_ADD_DIVISION, PRE_ADD_COUNTRY, PER_ADD_HOUSE, PER_ADD_ROAD, PER_ADD_THANA, PER_ADD_DISTRICT, PER_ADD_ZIP, PER_ADD_DIVISION, PER_ADD_COUNTRY, EMERGENCY_NAME, EMERGENCY_RELATION, EMERGENCY_PHONE, EMERGENCY_ADDRESS) VALUES (? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPersonalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int savePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getEmployeeId(),
        pMutablePersonalInformation.getFatherName(), pMutablePersonalInformation.getLastName(),
        pMutablePersonalInformation.getGender(), pMutablePersonalInformation.getBloodGroup(),
        pMutablePersonalInformation.getFatherName(), pMutablePersonalInformation.getMotherName(),
        pMutablePersonalInformation.getNationality(), pMutablePersonalInformation.getReligion(),
        pMutablePersonalInformation.getDateOfBirth(), pMutablePersonalInformation.getNationalId(),
        pMutablePersonalInformation.getMaritalStatus(), pMutablePersonalInformation.getSpouseName(),
        pMutablePersonalInformation.getSpouseNationalId(), pMutablePersonalInformation.getWebsite(),
        pMutablePersonalInformation.getOrganizationalEmail(), pMutablePersonalInformation.getPersonalEmail(),
        pMutablePersonalInformation.getMobileNumber(), pMutablePersonalInformation.getPhoneNumber(),
        pMutablePersonalInformation.getPresentAddressHouse(), pMutablePersonalInformation.getPresentAddressRoad(),
        pMutablePersonalInformation.getPresentAddressThana(), pMutablePersonalInformation.getPresentAddressDistrict(),
        pMutablePersonalInformation.getPresentAddressZip(), pMutablePersonalInformation.getPresentAddressDivision(),
        pMutablePersonalInformation.getPresentAddressCountry(), pMutablePersonalInformation.getPermanentAddressHouse(),
        pMutablePersonalInformation.getPermanentAddressRoad(), pMutablePersonalInformation.getPermanentAddressThana(),
        pMutablePersonalInformation.getPermanentAddressDistrict(),
        pMutablePersonalInformation.getPermanentAddressZip(),
        pMutablePersonalInformation.getPermanentAddressDivision(),
        pMutablePersonalInformation.getPermanentAddressCountry(),
        pMutablePersonalInformation.getEmergencyContactName(),
        pMutablePersonalInformation.getEmergencyContactRelation(),
        pMutablePersonalInformation.getEmergencyContactPhone(),
        pMutablePersonalInformation.getEmergencyContactAddress());
  }

  class RoleRowMapper implements RowMapper<PersonalInformation> {

    @Override
    public PersonalInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
      personalInformation.setEmployeeId(resultSet.getInt("employee_id"));
      personalInformation.setFirstName(resultSet.getString("first_name"));
      personalInformation.setLastName(resultSet.getString("last_name"));
      personalInformation.setGender(resultSet.getString("gender"));
      personalInformation.setBloodGroup(resultSet.getString("blood_group"));
      personalInformation.setFatherName(resultSet.getString("father_name"));
      personalInformation.setMotherName(resultSet.getString("mother_name"));
      personalInformation.setNationality(resultSet.getString("nationality"));
      personalInformation.setReligion(resultSet.getString("religion"));
      personalInformation.setDateOfBirth(resultSet.getString("date_of_birth"));
      personalInformation.setNationalId(resultSet.getInt("national_id_card"));
      personalInformation.setMaritalStatus(resultSet.getInt("marital_status"));
      personalInformation.setSpouseName(resultSet.getString("spouse_name"));
      personalInformation.setSpouseNationalId(resultSet.getInt("spouse_national_id_card"));
      personalInformation.setWebsite(resultSet.getString("website"));
      personalInformation.setOrganizationalEmail(resultSet.getString("organizational_email"));
      personalInformation.setPersonalEmail(resultSet.getString("personal_email"));
      personalInformation.setMobileNumber(resultSet.getString("mobile"));
      personalInformation.setPhoneNumber(resultSet.getString("phone"));
      personalInformation.setPresentAddressHouse(resultSet.getString("pre_add_house"));
      personalInformation.setPresentAddressRoad(resultSet.getString("pre_add_road"));
      personalInformation.setPresentAddressThana(resultSet.getString("pre_add_thana"));
      personalInformation.setPresentAddressDistrict(resultSet.getString("pre_add_district"));
      personalInformation.setPresentAddressZip(resultSet.getString("pre_add_zip"));
      personalInformation.setPresentAddressDivision(resultSet.getString("pre_add_division"));
      personalInformation.setPresentAddressCountry(resultSet.getString("pre_add_country"));
      personalInformation.setPermanentAddressHouse(resultSet.getString("per_add_house"));
      personalInformation.setPermanentAddressRoad(resultSet.getString("per_add_road"));
      personalInformation.setPermanentAddressThana(resultSet.getString("per_add_thana"));
      personalInformation.setPermanentAddressDistrict(resultSet.getString("per_add_district"));
      personalInformation.setPermanentAddressZip(resultSet.getString("per_add_zip"));
      personalInformation.setPermanentAddressDivision(resultSet.getString("per_add_division"));
      personalInformation.setPermanentAddressCountry(resultSet.getString("per_add_country"));
      personalInformation.setEmergencyContactName(resultSet.getString("emergency_name"));
      personalInformation.setEmergencyContactRelation(resultSet.getString("emergency_relation"));
      personalInformation.setEmergencyContactPhone(resultSet.getString("emergency_phone"));
      personalInformation.setEmergencyContactAddress(resultSet.getString("emergency_address"));
      return personalInformation;
    }
  }
}
