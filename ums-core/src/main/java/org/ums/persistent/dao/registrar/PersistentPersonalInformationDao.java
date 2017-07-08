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
          + "WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, PRE_ADD_COUNTRY, "
          + "PRE_ADD_DIVISION, PRE_ADD_DISTRICT, PRE_ADD_THANA, PRE_ADD_POST_CODE, PER_ADD_LINE1, PER_ADD_LINE2, PER_ADD_COUNTRY, "
          + "PER_ADD_DIVISION, PER_ADD_DISTRICT, PER_ADD_THANA,  PER_ADD_POST_CODE, EMERGENCY_NAME, EMERGENCY_RELATION, "
          + "EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED) VALUES (? ,? ,?, ?, ? ,? ,? ,? ,? ,?, "
          + "? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,?, " + getLastModifiedSql()
          + ")";

  static String GET_ONE =
      "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, NATIONALITY, RELIGION, "
          + "DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME, "
          + "SPOUSE_NID_NO, WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, "
          + "PRE_ADD_COUNTRY, PRE_ADD_DIVISION, PRE_ADD_DISTRICT, PRE_ADD_THANA, PRE_ADD_POST_CODE, PER_ADD_LINE1, "
          + "PER_ADD_LINE2, PER_ADD_COUNTRY, PER_ADD_DIVISION, PER_ADD_DISTRICT, PER_ADD_THANA, PER_ADD_POST_CODE, "
          + "EMERGENCY_NAME, EMERGENCY_RELATION, EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED FROM EMP_PERSONAL_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_PERSONAL_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_PERSONAL_INFO SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, BLOOD_GROUP = ?, FATHER_NAME = ?, "
          + "MOTHER_NAME = ?, NATIONALITY = ?, RELIGION = ?, DATE_OF_BIRTH = ?, NID_NO = ?, "
          + "MARITAL_STATUS = ?, SPOUSE_NAME = ?, SPOUSE_NID_NO = ?, WEBSITE = ?, ORGANIZATIONAL_EMAIL = ?, "
          + "PERSONAL_EMAIL = ?, MOBILE = ?, PHONE = ?, PRE_ADD_LINE1 = ?, PRE_ADD_LINE2 = ?, PRE_ADD_COUNTRY = ?, "
          + "PRE_ADD_DIVISION = ?, PRE_ADD_DISTRICT = ?, PRE_ADD_THANA = ?, PRE_ADD_POST_CODE = ?, PER_ADD_LINE1 = ?, "
          + "PER_ADD_LINE2 = ?, PER_ADD_COUNTRY = ?, PER_ADD_DIVISION = ?, PER_ADD_DISTRICT = ?, PER_ADD_THANA = ?, PER_ADD_POST_CODE = ?, "
          + "EMERGENCY_NAME = ?, EMERGENCY_RELATION = ?, EMERGENCY_PHONE = ?, EMERGENCY_ADDRESS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPersonalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public PersonalInformation get(final String pId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pId}, new PersistentPersonalInformationDao.RoleRowMapper());
  }

  @Override
  public int delete(final MutablePersonalInformation pMutablePersonalInformation) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getId());
  }

  @Override
  public String create(MutablePersonalInformation pMutablePersonalInformation) {
    String query = INSERT_ONE;
    return String.valueOf(mJdbcTemplate.update(query, pMutablePersonalInformation.getId(), pMutablePersonalInformation
        .getFirstName(), pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
        pMutablePersonalInformation.getBloodGroup().getId(), pMutablePersonalInformation.getFatherName(),
        pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationality().getId(),
        pMutablePersonalInformation.getReligion().getId(), pMutablePersonalInformation.getDateOfBirth(),
        pMutablePersonalInformation.getNidNo(), pMutablePersonalInformation.getMaritalStatus().getId(),
        pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNidNo(),
        pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
        pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
        pMutablePersonalInformation.getPhoneNumber(), pMutablePersonalInformation.getPresentAddressLine1(),
        pMutablePersonalInformation.getPresentAddressLine2(),
        pMutablePersonalInformation.getPresentAddressCountry() == null ? null : pMutablePersonalInformation
            .getPresentAddressCountry().getId(), pMutablePersonalInformation.getPresentAddressDivision() == null ? null
            : pMutablePersonalInformation.getPresentAddressDivision().getId(), pMutablePersonalInformation
            .getPresentAddressDistrict() == null ? null : pMutablePersonalInformation.getPresentAddressDistrict()
            .getId(), pMutablePersonalInformation.getPresentAddressThana() == null ? null : pMutablePersonalInformation
            .getPresentAddressThana().getId(), pMutablePersonalInformation.getPresentAddressPostCode(),
        pMutablePersonalInformation.getPermanentAddressLine1(), pMutablePersonalInformation.getPermanentAddressLine2(),
        pMutablePersonalInformation.getPermanentAddressCountry() == null ? null : pMutablePersonalInformation
            .getPermanentAddressCountry().getId(),
        pMutablePersonalInformation.getPermanentAddressDivision() == null ? null : pMutablePersonalInformation
            .getPermanentAddressDivision().getId(),
        pMutablePersonalInformation.getPermanentAddressDistrict() == null ? null : pMutablePersonalInformation
            .getPermanentAddressDistrict().getId(),
        pMutablePersonalInformation.getPermanentAddressThana() == null ? null : pMutablePersonalInformation
            .getPermanentAddressThana().getId(), pMutablePersonalInformation.getPermanentAddressPostCode(),
        pMutablePersonalInformation.getEmergencyContactName(), pMutablePersonalInformation
            .getEmergencyContactRelation().getId(), pMutablePersonalInformation.getEmergencyContactPhone(),
        pMutablePersonalInformation.getEmergencyContactAddress()));
  }

  @Override
  public int update(MutablePersonalInformation pMutablePersonalInformation) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getFirstName(), pMutablePersonalInformation
        .getLastName(), pMutablePersonalInformation.getGender(), pMutablePersonalInformation.getBloodGroup().getId(),
        pMutablePersonalInformation.getFatherName(), pMutablePersonalInformation.getMotherName(),
        pMutablePersonalInformation.getNationality().getId(), pMutablePersonalInformation.getReligion().getId(),
        pMutablePersonalInformation.getDateOfBirth(), pMutablePersonalInformation.getNidNo(),
        pMutablePersonalInformation.getMaritalStatus().getId(), pMutablePersonalInformation.getSpouseName(),
        pMutablePersonalInformation.getSpouseNidNo(), pMutablePersonalInformation.getWebsite(),
        pMutablePersonalInformation.getOrganizationalEmail(), pMutablePersonalInformation.getPersonalEmail(),
        pMutablePersonalInformation.getMobileNumber(), pMutablePersonalInformation.getPhoneNumber(),
        pMutablePersonalInformation.getPresentAddressLine1(), pMutablePersonalInformation.getPresentAddressLine2(),
        pMutablePersonalInformation.getPresentAddressCountry() == null ? null : pMutablePersonalInformation
            .getPresentAddressCountry().getId(), pMutablePersonalInformation.getPresentAddressDivision() == null ? null
            : pMutablePersonalInformation.getPresentAddressDivision().getId(), pMutablePersonalInformation
            .getPresentAddressDistrict() == null ? null : pMutablePersonalInformation.getPresentAddressDistrict()
            .getId(), pMutablePersonalInformation.getPresentAddressThana() == null ? null : pMutablePersonalInformation
            .getPresentAddressThana().getId(), pMutablePersonalInformation.getPresentAddressPostCode(),
        pMutablePersonalInformation.getPermanentAddressLine1(), pMutablePersonalInformation.getPermanentAddressLine2(),
        pMutablePersonalInformation.getPermanentAddressCountry() == null ? null : pMutablePersonalInformation
            .getPermanentAddressCountry().getId(),
        pMutablePersonalInformation.getPermanentAddressDivision() == null ? null : pMutablePersonalInformation
            .getPermanentAddressDivision().getId(),
        pMutablePersonalInformation.getPermanentAddressDistrict() == null ? null : pMutablePersonalInformation
            .getPermanentAddressDistrict().getId(),
        pMutablePersonalInformation.getPermanentAddressThana() == null ? null : pMutablePersonalInformation
            .getPermanentAddressThana().getId(), pMutablePersonalInformation.getPermanentAddressPostCode(),
        pMutablePersonalInformation.getEmergencyContactName(), pMutablePersonalInformation
            .getEmergencyContactRelation().getId(), pMutablePersonalInformation.getEmergencyContactPhone(),
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
      personalInformation.setNidNo(resultSet.getString("nid_no"));
      personalInformation.setMaritalStatusId(resultSet.getInt("marital_status"));
      personalInformation.setSpouseName(resultSet.getString("spouse_name"));
      personalInformation.setSpouseNidNo(resultSet.getString("spouse_nid_no"));
      personalInformation.setWebsite(resultSet.getString("website"));
      personalInformation.setOrganizationalEmail(resultSet.getString("organizational_email"));
      personalInformation.setPersonalEmail(resultSet.getString("personal_email"));
      personalInformation.setMobileNumber(resultSet.getString("mobile"));
      personalInformation.setPhoneNumber(resultSet.getString("phone"));
      personalInformation.setPresentAddressLine1(resultSet.getString("pre_add_line1"));
      personalInformation.setPresentAddressLine2(resultSet.getString("pre_add_line2"));
      personalInformation.setPresentAddressCountryId(resultSet.getInt("pre_add_country"));
      personalInformation.setPresentAddressDivisionId(resultSet.getInt("pre_add_division"));
      personalInformation.setPresentAddressDistrictId(resultSet.getInt("pre_add_district"));
      personalInformation.setPresentAddressThanaId(resultSet.getInt("pre_add_thana"));
      personalInformation.setPresentAddressPostCode(resultSet.getString("pre_add_post_code"));
      personalInformation.setPermanentAddressLine1(resultSet.getString("per_add_line1"));
      personalInformation.setPermanentAddressLine2(resultSet.getString("per_add_line2"));
      personalInformation.setPermanentAddressCountryId(resultSet.getInt("per_add_country"));
      personalInformation.setPermanentAddressDivisionId(resultSet.getInt("per_add_division"));
      personalInformation.setPermanentAddressDistrictId(resultSet.getInt("per_add_district"));
      personalInformation.setPermanentAddressThanaId(resultSet.getInt("per_add_thana"));
      personalInformation.setPermanentAddressPostCode(resultSet.getString("per_add_post_code"));
      personalInformation.setEmergencyContactName(resultSet.getString("emergency_name"));
      personalInformation.setEmergencyContactRelationId(resultSet.getInt("emergency_relation"));
      personalInformation.setEmergencyContactPhone(resultSet.getString("emergency_phone"));
      personalInformation.setEmergencyContactAddress(resultSet.getString("emergency_address"));
      personalInformation.setLastModified(resultSet.getString("last_modified"));
      return personalInformation;
    }
  }
}
