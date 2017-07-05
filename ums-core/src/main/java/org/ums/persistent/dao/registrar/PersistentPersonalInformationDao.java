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
      "INSERT INTO EMP_PERSONAL_INFO (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME,"
          + " NATIONALITY, RELIGION, DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME, SPOUSE_NID_NO, "
          + "WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, PRE_ADD_THANA, "
          + "PRE_ADD_DISTRICT, PRE_ADD_POST_CODE, PRE_ADD_DIVISION, PRE_ADD_COUNTRY, PER_ADD_LINE1, PER_ADD_LINE2, PER_ADD_THANA, "
          + "PER_ADD_DISTRICT, PER_ADD_POST_CODE, PER_ADD_DIVISION, PER_ADD_COUNTRY, EMERGENCY_NAME, EMERGENCY_RELATION, "
          + "EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED) VALUES (? ,? ,?, ?, ? ,? ,? ,? ,? ,?, "
          + "? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,?, " + getLastModifiedSql()
          + ")";

  static String GET_ONE =
      "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, NATIONALITY, RELIGION, "
          + "DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME, "
          + "SPOUSE_NID_NO, WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, "
          + "PRE_ADD_THANA, PRE_ADD_DISTRICT, PRE_ADD_POST_CODE, PRE_ADD_DIVISION, PRE_ADD_COUNTRY, PER_ADD_LINE1, "
          + "PER_ADD_LINE2, PER_ADD_THANA, PER_ADD_DISTRICT, PER_ADD_POST_CODE, PER_ADD_DIVISION, PER_ADD_COUNTRY, "
          + "EMERGENCY_NAME, EMERGENCY_RELATION, EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED FROM EMP_PERSONAL_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_PERSONAL_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_PERSONAL_INFO SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, BLOOD_GROUP = ?, FATHER_NAME = ?, "
          + "MOTHER_NAME = ?, NATIONALITY = ?, RELIGION = ?, DATE_OF_BIRTH = ?, NID_NO = ?, "
          + "MARITAL_STATUS = ?, SPOUSE_NAME = ?, SPOUSE_NID_NO = ?, WEBSITE = ?, ORGANIZATIONAL_EMAIL = ?, "
          + "PERSONAL_EMAIL = ?, MOBILE = ?, PHONE = ?, PRE_ADD_LINE1 = ?, PRE_ADD_LINE2 = ?, PRE_ADD_THANA = ?, "
          + "PRE_ADD_DISTRICT = ?, PRE_ADD_POST_CODE = ?, PRE_ADD_DIVISION = ?, PRE_ADD_COUNTRY = ?, PER_ADD_LINE1 = ?, "
          + "PER_ADD_LINE2 = ?, PER_ADD_THANA = ?, PER_ADD_DISTRICT = ?, PER_ADD_POST_CODE = ?, PER_ADD_DIVISION = ?, PER_ADD_COUNTRY = ?, "
          + "EMERGENCY_NAME = ?, EMERGENCY_RELATION = ?, EMERGENCY_PHONE = ?, EMERGENCY_ADDRESS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPersonalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public PersonalInformation get(final String pId) {
    String query = GET_ONE + "Where employee_id = ?";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pId}, new PersistentPersonalInformationDao.RoleRowMapper());
  }

  @Override
  public int delete(final MutablePersonalInformation pMutablePersonalInformation) {
    String query = DELETE_ONE + "WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getId());
  }

  @Override
  public String create(MutablePersonalInformation pMutablePersonalInformation) {
    String query = INSERT_ONE;
    return String.valueOf(mJdbcTemplate.update(query, pMutablePersonalInformation.getId(),
        pMutablePersonalInformation.getFirstName(), pMutablePersonalInformation.getLastName(),
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
        pMutablePersonalInformation.getEmergencyContactAddress()));
  }

  @Override
  public int update(MutablePersonalInformation pMutablePersonalInformation) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getFirstName(),
        pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
        pMutablePersonalInformation.getBloodGroup(), pMutablePersonalInformation.getFatherName(),
        pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationality(),
        pMutablePersonalInformation.getReligion(), pMutablePersonalInformation.getDateOfBirth(),
        pMutablePersonalInformation.getNationalId(), pMutablePersonalInformation.getMaritalStatus(),
        pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNationalId(),
        pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
        pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
        pMutablePersonalInformation.getPhoneNumber(), pMutablePersonalInformation.getPresentAddressHouse(),
        pMutablePersonalInformation.getPresentAddressRoad(), pMutablePersonalInformation.getPresentAddressThana(),
        pMutablePersonalInformation.getPresentAddressDistrict(), pMutablePersonalInformation.getPresentAddressZip(),
        pMutablePersonalInformation.getPresentAddressDivision(),
        pMutablePersonalInformation.getPresentAddressCountry(), pMutablePersonalInformation.getPermanentAddressHouse(),
        pMutablePersonalInformation.getPermanentAddressRoad(), pMutablePersonalInformation.getPermanentAddressThana(),
        pMutablePersonalInformation.getPermanentAddressDistrict(),
        pMutablePersonalInformation.getPermanentAddressZip(),
        pMutablePersonalInformation.getPermanentAddressDivision(),
        pMutablePersonalInformation.getPermanentAddressCountry(),
        pMutablePersonalInformation.getEmergencyContactName(),
        pMutablePersonalInformation.getEmergencyContactRelation(),
        pMutablePersonalInformation.getEmergencyContactPhone(),
        pMutablePersonalInformation.getEmergencyContactAddress(), pMutablePersonalInformation.getId());
  }

  class RoleRowMapper implements RowMapper<PersonalInformation> {

    @Override
    public PersonalInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPersonalInformation personalInformation = new PersistentPersonalInformation();
      personalInformation.setId(resultSet.getString("employee_id"));
      personalInformation.setFirstName(resultSet.getString("first_name"));
      personalInformation.setLastName(resultSet.getString("last_name"));
      personalInformation.setGender(resultSet.getString("gender"));
      personalInformation.setBloodGroupId(resultSet.getInt("blood_group"));
      personalInformation.setFatherName(resultSet.getString("father_name"));
      personalInformation.setMotherName(resultSet.getString("mother_name"));
      personalInformation.setNationalityId(resultSet.getInt("nationality"));
      personalInformation.setReligionId(resultSet.getInt("religion"));
      personalInformation.setDateOfBirth(resultSet.getDate("date_of_birth"));
      personalInformation.setNationalId(resultSet.getString("nid_no"));
      personalInformation.setMaritalStatusId(resultSet.getInt("marital_status"));
      personalInformation.setSpouseName(resultSet.getString("spouse_name"));
      personalInformation.setSpouseNationalId(resultSet.getString("spouse_nid_no"));
      personalInformation.setWebsite(resultSet.getString("website"));
      personalInformation.setOrganizationalEmail(resultSet.getString("organizational_email"));
      personalInformation.setPersonalEmail(resultSet.getString("personal_email"));
      personalInformation.setMobileNumber(resultSet.getString("mobile"));
      personalInformation.setPhoneNumber(resultSet.getString("phone"));
      personalInformation.setPresentAddressHouse(resultSet.getString("pre_add_line1"));
      personalInformation.setPresentAddressRoad(resultSet.getString("pre_add_line2"));
      personalInformation.setPresentAddressThanaId(resultSet.getInt("pre_add_thana"));
      personalInformation.setPresentAddressDistrictId(resultSet.getInt("pre_add_district"));
      personalInformation.setPresentAddressZip(resultSet.getInt("pre_add_post_code"));
      personalInformation.setPresentAddressDivisionId(resultSet.getInt("pre_add_division"));
      personalInformation.setPresentAddressCountryId(resultSet.getInt("pre_add_country"));
      personalInformation.setPermanentAddressHouse(resultSet.getString("per_add_line1"));
      personalInformation.setPermanentAddressRoad(resultSet.getString("per_add_line2"));
      personalInformation.setPermanentAddressThanaId(resultSet.getInt("per_add_thana"));
      personalInformation.setPermanentAddressDistrictId(resultSet.getInt("per_add_district"));
      personalInformation.setPermanentAddressZip(resultSet.getInt("per_add_post_code"));
      personalInformation.setPermanentAddressDivisionId(resultSet.getInt("per_add_division"));
      personalInformation.setPermanentAddressCountryId(resultSet.getInt("per_add_country"));
      personalInformation.setEmergencyContactName(resultSet.getString("emergency_name"));
      personalInformation.setEmergencyContactRelationId(resultSet.getInt("emergency_relation"));
      personalInformation.setEmergencyContactPhone(resultSet.getString("emergency_phone"));
      personalInformation.setEmergencyContactAddress(resultSet.getString("emergency_address"));
      personalInformation.setLastModified(resultSet.getString("last_modified"));
      return personalInformation;
    }
  }
}
