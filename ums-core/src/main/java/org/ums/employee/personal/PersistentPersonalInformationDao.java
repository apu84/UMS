package org.ums.employee.personal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersistentPersonalInformationDao extends PersonalInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_PERSONAL_INFO (EMPLOYEE_ID, NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME,"
          + " NATIONALITY, RELIGION, DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME, SPOUSE_NID_NO, "
          + "WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, PRE_ADD_COUNTRY, "
          + "PRE_ADD_DIVISION, PRE_ADD_DISTRICT, PRE_ADD_THANA, PRE_ADD_POST_CODE, PER_ADD_LINE1, PER_ADD_LINE2, PER_ADD_COUNTRY, "
          + "PER_ADD_DIVISION, PER_ADD_DISTRICT, PER_ADD_THANA,  PER_ADD_POST_CODE, EMERGENCY_NAME, EMERGENCY_RELATION, "
          + "EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED) VALUES (? ,? ,?, ?, ? ,? ,? ,? ,? ,?, "
          + "? ,? ,?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ? ,? ,? ,? ,? ,?, ? ,? ,?, ?, ? ,? ,?, " + getLastModifiedSql()
          + ")";

  static String GET_ONE =
      "SELECT EMPLOYEE_ID, NAME, GENDER, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, NATIONALITY, RELIGION, "
          + "DATE_OF_BIRTH, NID_NO, MARITAL_STATUS, SPOUSE_NAME, "
          + "SPOUSE_NID_NO, WEBSITE, ORGANIZATIONAL_EMAIL, PERSONAL_EMAIL, MOBILE, PHONE, PRE_ADD_LINE1, PRE_ADD_LINE2, "
          + "PRE_ADD_COUNTRY, PRE_ADD_DIVISION, PRE_ADD_DISTRICT, PRE_ADD_THANA, PRE_ADD_POST_CODE, PER_ADD_LINE1, "
          + "PER_ADD_LINE2, PER_ADD_COUNTRY, PER_ADD_DIVISION, PER_ADD_DISTRICT, PER_ADD_THANA, PER_ADD_POST_CODE, "
          + "EMERGENCY_NAME, EMERGENCY_RELATION, EMERGENCY_PHONE, EMERGENCY_ADDRESS, LAST_MODIFIED FROM EMP_PERSONAL_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_PERSONAL_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_PERSONAL_INFO SET NAME = ?, GENDER = ?, BLOOD_GROUP = ?, FATHER_NAME = ?, "
          + "MOTHER_NAME = ?, NATIONALITY = ?, RELIGION = ?, DATE_OF_BIRTH = ?, NID_NO = ?, "
          + "MARITAL_STATUS = ?, SPOUSE_NAME = ?, SPOUSE_NID_NO = ?, WEBSITE = ?, ORGANIZATIONAL_EMAIL = ?, "
          + "PERSONAL_EMAIL = ?, MOBILE = ?, PHONE = ?, PRE_ADD_LINE1 = ?, PRE_ADD_LINE2 = ?, PRE_ADD_COUNTRY = ?, "
          + "PRE_ADD_DIVISION = ?, PRE_ADD_DISTRICT = ?, PRE_ADD_THANA = ?, PRE_ADD_POST_CODE = ?, PER_ADD_LINE1 = ?, "
          + "PER_ADD_LINE2 = ?, PER_ADD_COUNTRY = ?, PER_ADD_DIVISION = ?, PER_ADD_DISTRICT = ?, PER_ADD_THANA = ?, PER_ADD_POST_CODE = ?, "
          + "EMERGENCY_NAME = ?, EMERGENCY_RELATION = ?, EMERGENCY_PHONE = ?, EMERGENCY_ADDRESS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_PERSONAL_INFO ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPersonalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

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
  public boolean exists(String pId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, Boolean.class);
  }

  @Override
  public String create(MutablePersonalInformation pMutablePersonalInformation) {
    String query = INSERT_ONE;
    mJdbcTemplate.update(query, pMutablePersonalInformation.getId(), pMutablePersonalInformation.getName(),
        pMutablePersonalInformation.getGender(), pMutablePersonalInformation.getBloodGroupId(),
        pMutablePersonalInformation.getFatherName(), pMutablePersonalInformation.getMotherName(),
        pMutablePersonalInformation.getNationalityId(), pMutablePersonalInformation.getReligionId(),
        pMutablePersonalInformation.getDateOfBirth(), pMutablePersonalInformation.getNidNo(),
        pMutablePersonalInformation.getMaritalStatusId(), pMutablePersonalInformation.getSpouseName(),
        pMutablePersonalInformation.getSpouseNidNo(), pMutablePersonalInformation.getWebsite(),
        pMutablePersonalInformation.getOrganizationalEmail(), pMutablePersonalInformation.getPersonalEmail(),
        pMutablePersonalInformation.getMobileNumber(), pMutablePersonalInformation.getPhoneNumber(),
        pMutablePersonalInformation.getPresentAddressLine1(), pMutablePersonalInformation.getPresentAddressLine2(),
        pMutablePersonalInformation.getPresentAddressCountryId(),
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
    return mJdbcTemplate.update(
        query,
        pMutablePersonalInformation.getName(),
        pMutablePersonalInformation.getGender(),
        pMutablePersonalInformation.getBloodGroupId(),
        pMutablePersonalInformation.getFatherName(),
        pMutablePersonalInformation.getMotherName(),
        pMutablePersonalInformation.getNationalityId(),
        pMutablePersonalInformation.getReligionId(),
        pMutablePersonalInformation.getDateOfBirth(),
        pMutablePersonalInformation.getNidNo(),
        pMutablePersonalInformation.getMaritalStatusId(),
        pMutablePersonalInformation.getSpouseName(),
        pMutablePersonalInformation.getSpouseNidNo(),
        pMutablePersonalInformation.getWebsite(),
        pMutablePersonalInformation.getOrganizationalEmail(),
        pMutablePersonalInformation.getPersonalEmail().equals("") ? "-" : pMutablePersonalInformation
            .getPersonalEmail(), pMutablePersonalInformation.getMobileNumber(), pMutablePersonalInformation
            .getPhoneNumber(), pMutablePersonalInformation.getPresentAddressLine1(), pMutablePersonalInformation
            .getPresentAddressLine2(), pMutablePersonalInformation.getPresentAddressCountryId(),
        pMutablePersonalInformation.getPresentAddressDivisionId(), pMutablePersonalInformation
            .getPresentAddressDistrictId(), pMutablePersonalInformation.getPresentAddressThanaId(),
        pMutablePersonalInformation.getPresentAddressPostCode(),
        pMutablePersonalInformation.getPermanentAddressLine1(), pMutablePersonalInformation.getPermanentAddressLine2(),
        pMutablePersonalInformation.getPermanentAddressCountryId(), pMutablePersonalInformation
            .getPermanentAddressDivisionId(), pMutablePersonalInformation.getPermanentAddressDistrictId(),
        pMutablePersonalInformation.getPermanentAddressThanaId(), pMutablePersonalInformation
            .getPermanentAddressPostCode(), pMutablePersonalInformation.getEmergencyContactName(),
        pMutablePersonalInformation.getEmergencyContactRelationId(), pMutablePersonalInformation
            .getEmergencyContactPhone(), pMutablePersonalInformation.getEmergencyContactAddress(),
        pMutablePersonalInformation.getId());
  }

  class PersonalInformationRowMapper implements RowMapper<PersonalInformation> {
    @Override
    public PersonalInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPersonalInformation personalInformation = new PersistentPersonalInformation();
      personalInformation.setId(resultSet.getString("EMPLOYEE_ID"));
      personalInformation.setName(resultSet.getString("NAME"));
      personalInformation.setGender(resultSet.getString("GENDER"));
      personalInformation.setBloodGroupId(resultSet.getInt("BLOOD_GROUP"));
      personalInformation.setFatherName(resultSet.getString("FATHER_NAME"));
      personalInformation.setMotherName(resultSet.getString("MOTHER_NAME"));
      personalInformation.setNationalityId(resultSet.getInt("NATIONALITY"));
      personalInformation.setReligionId(resultSet.getInt("RELIGION"));
      personalInformation.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
      personalInformation.setNidNo(resultSet.getString("NID_NO"));
      personalInformation.setMaritalStatusId(resultSet.getInt("MARITAL_STATUS"));
      personalInformation.setSpouseName(resultSet.getString("SPOUSE_NAME"));
      personalInformation.setSpouseNidNo(resultSet.getString("SPOUSE_NID_NO"));
      personalInformation.setWebsite(resultSet.getString("WEBSITE"));
      personalInformation.setOrganizationalEmail(resultSet.getString("ORGANIZATIONAL_EMAIL"));
      personalInformation.setPersonalEmail(resultSet.getString("PERSONAL_EMAIL"));
      personalInformation.setMobileNumber(resultSet.getString("MOBILE"));
      personalInformation.setPhoneNumber(resultSet.getString("PHONE"));
      personalInformation.setPresentAddressLine1(resultSet.getString("PRE_ADD_LINE1"));
      personalInformation.setPresentAddressLine2(resultSet.getString("PRE_ADD_LINE2"));
      personalInformation.setPresentAddressCountryId(resultSet.getInt("PRE_ADD_COUNTRY"));
      personalInformation.setPresentAddressDivisionId(resultSet.getInt("PRE_ADD_DIVISION"));
      personalInformation.setPresentAddressDistrictId(resultSet.getInt("PRE_ADD_DISTRICT"));
      personalInformation.setPresentAddressThanaId(resultSet.getInt("PRE_ADD_THANA"));
      personalInformation.setPresentAddressPostCode(resultSet.getString("PRE_ADD_POST_CODE"));
      personalInformation.setPermanentAddressLine1(resultSet.getString("PER_ADD_LINE1"));
      personalInformation.setPermanentAddressLine2(resultSet.getString("PER_ADD_LINE2"));
      personalInformation.setPermanentAddressCountryId(resultSet.getInt("PER_ADD_COUNTRY"));
      personalInformation.setPermanentAddressDivisionId(resultSet.getInt("PER_ADD_DIVISION"));
      personalInformation.setPermanentAddressDistrictId(resultSet.getInt("PER_ADD_DISTRICT"));
      personalInformation.setPermanentAddressThanaId(resultSet.getInt("PER_ADD_THANA"));
      personalInformation.setPermanentAddressPostCode(resultSet.getString("PER_ADD_POST_CODE"));
      personalInformation.setEmergencyContactName(resultSet.getString("EMERGENCY_NAME"));
      personalInformation.setEmergencyContactRelationId(resultSet.getInt("EMERGENCY_RELATION"));
      personalInformation.setEmergencyContactPhone(resultSet.getString("EMERGENCY_PHONE"));
      personalInformation.setEmergencyContactAddress(resultSet.getString("EMERGENCY_ADDRESS"));
      personalInformation.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return personalInformation;
    }
  }
}
