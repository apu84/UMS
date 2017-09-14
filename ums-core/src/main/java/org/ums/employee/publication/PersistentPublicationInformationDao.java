package org.ums.employee.publication;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentPublicationInformationDao extends PublicationInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_PUBLICATION_INFO (ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME,"
          + " DATE_OF_PUBLICATION, TYPE, WEB_LINK, ISSN, ISSUE,"
          + " VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PAGES, APPLIED_ON, ACTION_TAKEN_ON,"
          + " LAST_MODIFIED) VALUES (?, ? ,? ,?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql()
          + ")";

  static String GET_ONE = "SELECT ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME, "
      + " DATE_OF_PUBLICATION, TYPE, WEB_LINK," + " ISSN, ISSUE, VOLUME, JOURNAL_NAME, COUNTRY, STATUS, "
      + " PAGES, APPLIED_ON, " + " ACTION_TAKEN_ON, LAST_MODIFIED FROM EMP_PUBLICATION_INFO ";

  static String DELETE_ALL = "DELETE FROM EMP_PUBLICATION_INFO ";

  static String UPDATE_STATUS = "UPDATE EMP_PUBLICATION_INFO SET STATUS=?, ACTION_TAKEN_ON = ? , LAST_MODIFIED="
      + getLastModifiedSql() + " ";

  static String UPDATE_ALL = "UPDATE EMP_PUBLICATION_INFO SET TITLE = ?, INTEREST_GENRE = ?, PUBLISHER_NAME = ?,"
      + " DATE_OF_PUBLICATION = ?, TYPE = ?, WEB_LINK = ?, ISSN = ?, ISSUE = ?,"
      + " VOLUME = ?, JOURNAL_NAME = ?, COUNTRY = ?, STATUS = ?, PAGES = ?, APPLIED_ON = ?," + " LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  static String PUBLICATION_LENGTH = "SELECT COUNT(*) FROM EMP_PUBLICATION_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPublicationInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int savePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeePublicationInformationParams(pMutablePublicationInformation)).length;
  }

  @Override
  public int deletePublicationInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ? AND STATUS = '0' ";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  @Override
  public int updatePublicationStatus(MutablePublicationInformation pMutablePublicationInformation) {
    String query = UPDATE_STATUS + "WHERE ID=?";
    return mJdbcTemplate.update(query, pMutablePublicationInformation.getStatus(),
        pMutablePublicationInformation.getActionTakenOn(), pMutablePublicationInformation.getId());
  }

  private List<Object[]> getEmployeePublicationInformationParams(
      List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation publicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {mIdGenerator.getNumericId(), publicationInformation.getEmployeeId(),
          publicationInformation.getTitle(), publicationInformation.getInterestGenre(),
          publicationInformation.getPublisherName(), publicationInformation.getDateOfPublication(),
          publicationInformation.getTypeId(), publicationInformation.getWebLink(), publicationInformation.getISSN(),
          publicationInformation.getIssue(), publicationInformation.getVolume(),
          publicationInformation.getJournalName(), publicationInformation.getCountry().getId(),
          publicationInformation.getStatus(), publicationInformation.getPages(), publicationInformation.getAppliedOn(),
          publicationInformation.getActionTakenOn()});

    }
    return params;
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(final String pEmployeeId) {
    String query = GET_ONE + " Where EMPLOYEE_ID = ? ORDER BY APPLIED_ON";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public List<PublicationInformation> getPublicationInformationWithPagination(final String pEmployeeId,
      final String pPublicationStatus, final int pPageNumber, final int pItemPerPage) {
    String query =
        "SELECT ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME,"
            + " DATE_OF_PUBLICATION, TYPE, WEB_LINK, ISSN, ISSUE,"
            + " VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PAGES, APPLIED_ON, ACTION_TAKEN_ON, LAST_MODIFIED, row_number FROM ( SELECT a.*,ROWNUM row_number FROM ( "
            + " SELECT * FROM EMP_PUBLICATION_INFO " + " WHERE EMPLOYEE_ID=? and STATUS = ? ORDER BY APPLIED_ON) a "
            + "  WHERE ROWNUM < ((" + pPageNumber + " * " + pItemPerPage + ") + 1)) " + "WHERE row_number >= ((("
            + pPageNumber + " - 1) * " + pItemPerPage + ") + 1)";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId, pPublicationStatus},
        new PersistentPublicationInformationDao.customRoleRowMapper());
  }

  @Override
  public List<PublicationInformation> getPublicationInformationWithPagination(final String pEmployeeId,
      final int pPageNumber, final int pItemPerPage) {
    String query =
        "SELECT ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME,"
            + " DATE_OF_PUBLICATION, TYPE, WEB_LINK, ISSN, ISSUE,"
            + " VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PAGES, APPLIED_ON, ACTION_TAKEN_ON, LAST_MODIFIED, row_number FROM ( SELECT a.*,ROWNUM row_number FROM ( "
            + " SELECT * FROM EMP_PUBLICATION_INFO " + " WHERE EMPLOYEE_ID=? ORDER BY APPLIED_ON) a "
            + "  WHERE ROWNUM < ((" + pPageNumber + " * " + pItemPerPage + ") + 1)) " + "WHERE row_number >= ((("
            + pPageNumber + " - 1) * " + pItemPerPage + ") + 1)";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId},
        new PersistentPublicationInformationDao.customRoleRowMapper());
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(final String pEmployeeId, final String pStatus) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ? AND STATUS = ?";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId, pStatus},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public List<PublicationInformation> getPublicationInformation(final String pPublicationStatus) {
    String query = GET_ONE + " WHERE STATUS = ?";
    return mJdbcTemplate.query(query, new Object[] {pPublicationStatus},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public int updatePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    String query = UPDATE_ALL + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutablePublicationInformation)).length;
  }

  @Override
  public int getLengthOfPublicationList(final String pEmployeeId, final String pPublicationStatus) {
    String query = PUBLICATION_LENGTH + " WHERE EMPLOYEE_ID = ? AND STATUS = ? ";
    return 0;
  }

  private List<Object[]> getUpdateParams(List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation pPublicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {pPublicationInformation.getTitle(), pPublicationInformation.getInterestGenre(),
          pPublicationInformation.getPublisherName(), pPublicationInformation.getDateOfPublication(),
          pPublicationInformation.getTypeId(), pPublicationInformation.getWebLink(), pPublicationInformation.getISSN(),
          pPublicationInformation.getIssue(), pPublicationInformation.getVolume(),
          pPublicationInformation.getJournalName(), pPublicationInformation.getCountry().getId(),
          pPublicationInformation.getStatus(), pPublicationInformation.getPages(),
          pPublicationInformation.getAppliedOn(), pPublicationInformation.getId(),
          pPublicationInformation.getEmployeeId()});
    }
    return params;
  }

  @Override
  public int deletePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    String query = DELETE_ALL + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParams(pMutablePublicationInformation)).length;
  }

  private List<Object[]> getDeleteParams(List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation pPublicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {pPublicationInformation.getId(), pPublicationInformation.getEmployeeId()});
    }

    return params;
  }

  class RoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setId(resultSet.getLong("id"));
      publicationInformation.setEmployeeId(resultSet.getString("employee_id"));
      publicationInformation.setTitle(resultSet.getString("title"));
      publicationInformation.setInterestGenre(resultSet.getString("interest_genre"));
      publicationInformation.setPublisherName(resultSet.getString("publisher_name"));
      publicationInformation.setDateOfPublication(resultSet.getDate("date_of_publication"));
      publicationInformation.setTypeId(resultSet.getInt("type"));
      publicationInformation.setWebLink(resultSet.getString("web_link"));
      publicationInformation.setISSN(resultSet.getString("issn"));
      publicationInformation.setIssue(resultSet.getString("issue"));
      publicationInformation.setVolume(resultSet.getString("volume"));
      publicationInformation.setJournalName(resultSet.getString("journal_name"));
      publicationInformation.setCountryId(resultSet.getInt("country"));
      publicationInformation.setStatus(resultSet.getString("status"));
      publicationInformation.setPages(resultSet.getString("pages"));
      publicationInformation.setAppliedOn(resultSet.getDate("applied_on"));
      publicationInformation.setActionTakenOn(resultSet.getDate("action_taken_on"));
      publicationInformation.setLastModified(resultSet.getString("last_modified"));
      return publicationInformation;

    }
  }

  class customRoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setId(resultSet.getLong("id"));
      publicationInformation.setEmployeeId(resultSet.getString("employee_id"));
      publicationInformation.setTitle(resultSet.getString("title"));
      publicationInformation.setInterestGenre(resultSet.getString("interest_genre"));
      publicationInformation.setPublisherName(resultSet.getString("publisher_name"));
      publicationInformation.setDateOfPublication(resultSet.getDate("date_of_publication"));
      publicationInformation.setTypeId(resultSet.getInt("type"));
      publicationInformation.setWebLink(resultSet.getString("web_link"));
      publicationInformation.setISSN(resultSet.getString("issn"));
      publicationInformation.setIssue(resultSet.getString("issue"));
      publicationInformation.setVolume(resultSet.getString("volume"));
      publicationInformation.setJournalName(resultSet.getString("journal_name"));
      publicationInformation.setCountryId(resultSet.getInt("country"));
      publicationInformation.setStatus(resultSet.getString("status"));
      publicationInformation.setPages(resultSet.getString("pages"));
      publicationInformation.setLastModified(resultSet.getString("last_modified"));
      publicationInformation.setActionTakenOn(resultSet.getDate("action_taken_on"));
      publicationInformation.setAppliedOn(resultSet.getDate("applied_on"));
      publicationInformation.setRowNumber(resultSet.getInt("row_number"));
      return publicationInformation;

    }
  }
}
