package org.ums.employee.personal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

  static String EXISTS =
      "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, NATIONALITY, RELIGION,"
          + " DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME,"
          + "SPOUSE_NID_NO, WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2,"
          + " PRE_ADD_COUNTRY, PRE_ADD_DIVISION, PRE_ADD_DISTRICT, PRE_ADD_THANA, PRE_ADD_POST_CODE, PER_ADD_LINE1,"
          + "PER_ADD_LINE2, PER_ADD_COUNTRY, PER_ADD_DIVISION, PER_ADD_DISTRICT, PER_ADD_THANA, PER_ADD_POST_CODE,"
          + "EMERGENCY_NAME, EMERGENCY_RELATION, EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED"
          + "FROM EMP_PERSONAL_INFO WHERE EXISTS (SELECT EMPLOYEE_ID FROM EMP_PERSONAL_INFO)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPersonalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  /*
   * @Override public PersonalInformation getPersonalInformation(final String pId) { String query =
   * GET_ONE + " WHERE EMPLOYEE_ID = ?"; return mJdbcTemplate .queryForObject(query, new Object[]
   * {pId}, new PersistentPersonalInformationDao.PersonalInformationRowMapper()); }
   * 
   * @Override public int deletePersonalInformation(final MutablePersonalInformation
   * pMutablePersonalInformation) { String query = DELETE_ONE + " WHERE EMPLOYEE_ID = ?"; return
   * mJdbcTemplate.update(query, pMutablePersonalInformation.getId()); }
   * 
   * @Override public int savePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { String query = INSERT_ONE; return mJdbcTemplate.update(query,
   * pMutablePersonalInformation.getId(), pMutablePersonalInformation.getFirstName(),
   * pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
   * pMutablePersonalInformation.getBloodGroupId(), pMutablePersonalInformation.getFatherName(),
   * pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationalityId(),
   * pMutablePersonalInformation.getReligionId(), pMutablePersonalInformation.getDateOfBirth(),
   * pMutablePersonalInformation.getNidNo(), pMutablePersonalInformation.getMaritalStatusId(),
   * pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNidNo(),
   * pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
   * pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
   * pMutablePersonalInformation.getPhoneNumber(),
   * pMutablePersonalInformation.getPresentAddressLine1(),
   * pMutablePersonalInformation.getPresentAddressLine2(),
   * pMutablePersonalInformation.getPresentAddressCountryId(),
   * pMutablePersonalInformation.getPresentAddressDivisionId(),
   * pMutablePersonalInformation.getPresentAddressDistrictId(),
   * pMutablePersonalInformation.getPresentAddressThanaId(),
   * pMutablePersonalInformation.getPresentAddressPostCode(),
   * pMutablePersonalInformation.getPermanentAddressLine1(),
   * pMutablePersonalInformation.getPermanentAddressLine2(),
   * pMutablePersonalInformation.getPermanentAddressCountryId(),
   * pMutablePersonalInformation.getPermanentAddressDivisionId(),
   * pMutablePersonalInformation.getPermanentAddressDistrictId(),
   * pMutablePersonalInformation.getPermanentAddressThanaId(),
   * pMutablePersonalInformation.getPermanentAddressPostCode(),
   * pMutablePersonalInformation.getEmergencyContactName(),
   * pMutablePersonalInformation.getEmergencyContactRelationId(),
   * pMutablePersonalInformation.getEmergencyContactPhone(),
   * pMutablePersonalInformation.getEmergencyContactAddress()); }
   * 
   * @Override public int updatePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ?"; return
   * mJdbcTemplate.update(query, pMutablePersonalInformation.getFirstName(),
   * pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
   * pMutablePersonalInformation.getBloodGroupId(), pMutablePersonalInformation.getFatherName(),
   * pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationalityId(),
   * pMutablePersonalInformation.getReligionId(), pMutablePersonalInformation.getDateOfBirth(),
   * pMutablePersonalInformation.getNidNo(), pMutablePersonalInformation.getMaritalStatusId(),
   * pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNidNo(),
   * pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
   * pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
   * pMutablePersonalInformation.getPhoneNumber(),
   * pMutablePersonalInformation.getPresentAddressLine1(),
   * pMutablePersonalInformation.getPresentAddressLine2(),
   * pMutablePersonalInformation.getPresentAddressCountryId(),
   * pMutablePersonalInformation.getPresentAddressDivisionId(),
   * pMutablePersonalInformation.getPresentAddressDistrictId(),
   * pMutablePersonalInformation.getPresentAddressThanaId(),
   * pMutablePersonalInformation.getPresentAddressPostCode(),
   * pMutablePersonalInformation.getPermanentAddressLine1(),
   * pMutablePersonalInformation.getPermanentAddressLine2(),
   * pMutablePersonalInformation.getPermanentAddressCountryId(),
   * pMutablePersonalInformation.getPermanentAddressDivisionId(),
   * pMutablePersonalInformation.getPermanentAddressDistrictId(),
   * pMutablePersonalInformation.getPermanentAddressThanaId(),
   * pMutablePersonalInformation.getPermanentAddressPostCode(),
   * pMutablePersonalInformation.getEmergencyContactName(),
   * pMutablePersonalInformation.getEmergencyContactRelationId(),
   * pMutablePersonalInformation.getEmergencyContactPhone(),
   * pMutablePersonalInformation.getEmergencyContactAddress(), pMutablePersonalInformation.getId());
   * }
   */

  @Override
  public PersonalInformation get(String pId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersonalInformationRowMapper());
  }

  @Override
  public List<PersonalInformation> getAll() {
    String query = GET_ONE;
    return mJdbcTemplate.query(query, new PersistentPersonalInformationDao.PersonalInformationRowMapper());
  }

  @Override
  public int delete(final MutablePersonalInformation pMutablePersonalInformation) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getId());
  }

  @Override
  public Optional<PersonalInformation> getByEmail(String pEmailAddress) {
    String query = GET_ONE + " WHERE PERSONAL_EMAIL = ? ";
    List<PersonalInformation> personalInformationList =
        mJdbcTemplate.query(query, new Object[] {pEmailAddress}, new PersonalInformationRowMapper());
    return personalInformationList.size() == 1 ? Optional.of(personalInformationList.get(0)) : Optional.empty();
  }

  @Override
  public String create(MutablePersonalInformation pMutablePersonalInformation) {
    String query = INSERT_ONE;
    mJdbcTemplate.update(query, pMutablePersonalInformation.getId(), pMutablePersonalInformation.getFirstName(),
        pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
        pMutablePersonalInformation.getBloodGroupId(), pMutablePersonalInformation.getFatherName(),
        pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationalityId(),
        pMutablePersonalInformation.getReligionId(), pMutablePersonalInformation.getDateOfBirth(),
        pMutablePersonalInformation.getNidNo(), pMutablePersonalInformation.getMaritalStatusId(),
        pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNidNo(),
        pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
        pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
        pMutablePersonalInformation.getPhoneNumber(), pMutablePersonalInformation.getPresentAddressLine1(),
        pMutablePersonalInformation.getPresentAddressLine2(), pMutablePersonalInformation.getPresentAddressCountryId(),
        pMutablePersonalInformation.getPresentAddressDivisionId(),
        pMutablePersonalInformation.getPresentAddressDistrictId(),
        pMutablePersonalInformation.getPresentAddressThanaId(),
        pMutablePersonalInformation.getPresentAddressPostCode(),
        pMutablePersonalInformation.getPermanentAddressLine1(), pMutablePersonalInformation.getPermanentAddressLine2(),
        pMutablePersonalInformation.getPermanentAddressCountryId(),
        pMutablePersonalInformation.getPermanentAddressDivisionId(),
        pMutablePersonalInformation.getPermanentAddressDistrictId(),
        pMutablePersonalInformation.getPermanentAddressThanaId(),
        pMutablePersonalInformation.getPermanentAddressPostCode(),
        pMutablePersonalInformation.getEmergencyContactName(),
        pMutablePersonalInformation.getEmergencyContactRelationId(),
        pMutablePersonalInformation.getEmergencyContactPhone(),
        pMutablePersonalInformation.getEmergencyContactAddress());

    return pMutablePersonalInformation.getId();
  }

  @Override
  public int update(MutablePersonalInformation pMutablePersonalInformation) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutablePersonalInformation.getFirstName(),
        pMutablePersonalInformation.getLastName(), pMutablePersonalInformation.getGender(),
        pMutablePersonalInformation.getBloodGroupId(), pMutablePersonalInformation.getFatherName(),
        pMutablePersonalInformation.getMotherName(), pMutablePersonalInformation.getNationalityId(),
        pMutablePersonalInformation.getReligionId(), pMutablePersonalInformation.getDateOfBirth(),
        pMutablePersonalInformation.getNidNo(), pMutablePersonalInformation.getMaritalStatusId(),
        pMutablePersonalInformation.getSpouseName(), pMutablePersonalInformation.getSpouseNidNo(),
        pMutablePersonalInformation.getWebsite(), pMutablePersonalInformation.getOrganizationalEmail(),
        pMutablePersonalInformation.getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(),
        pMutablePersonalInformation.getPhoneNumber(), pMutablePersonalInformation.getPresentAddressLine1(),
        pMutablePersonalInformation.getPresentAddressLine2(), pMutablePersonalInformation.getPresentAddressCountryId(),
        pMutablePersonalInformation.getPresentAddressDivisionId(),
        pMutablePersonalInformation.getPresentAddressDistrictId(),
        pMutablePersonalInformation.getPresentAddressThanaId(),
        pMutablePersonalInformation.getPresentAddressPostCode(),
        pMutablePersonalInformation.getPermanentAddressLine1(), pMutablePersonalInformation.getPermanentAddressLine2(),
        pMutablePersonalInformation.getPermanentAddressCountryId(),
        pMutablePersonalInformation.getPermanentAddressDivisionId(),
        pMutablePersonalInformation.getPermanentAddressDistrictId(),
        pMutablePersonalInformation.getPermanentAddressThanaId(),
        pMutablePersonalInformation.getPermanentAddressPostCode(),
        pMutablePersonalInformation.getEmergencyContactName(),
        pMutablePersonalInformation.getEmergencyContactRelationId(),
        pMutablePersonalInformation.getEmergencyContactPhone(),
        pMutablePersonalInformation.getEmergencyContactAddress(), pMutablePersonalInformation.getId());
  }

  class PersonalInformationRowMapper implements RowMapper<PersonalInformation> {

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
