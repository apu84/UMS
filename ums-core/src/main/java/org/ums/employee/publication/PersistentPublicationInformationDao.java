package org.ums.employee.publication;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentPublicationInformationDao extends PublicationInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_PUBLICATION_INFO (ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME,"
          + " DATE_OF_PUBLICATION, TYPE, WEB_LINK, ISSN, ISSUE,"
          + " VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PAGES, APPLIED_ON, ACTION_TAKEN_ON,"
          + " LAST_MODIFIED) VALUES (?, ? ,? ,?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql()
          + ")";

  static String GET_ALL = "SELECT ID, EMPLOYEE_ID, TITLE, INTEREST_GENRE, PUBLISHER_NAME, "
      + " DATE_OF_PUBLICATION, TYPE, WEB_LINK," + " ISSN, ISSUE, VOLUME, JOURNAL_NAME, COUNTRY, STATUS, "
      + " PAGES, APPLIED_ON, " + " ACTION_TAKEN_ON, LAST_MODIFIED FROM EMP_PUBLICATION_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_PUBLICATION_INFO ";

  static String UPDATE_ONE = "UPDATE EMP_PUBLICATION_INFO SET TITLE = ?, INTEREST_GENRE = ?, PUBLISHER_NAME = ?,"
      + " DATE_OF_PUBLICATION = ?, TYPE = ?, WEB_LINK = ?, ISSN = ?, ISSUE = ?,"
      + " VOLUME = ?, JOURNAL_NAME = ?, COUNTRY = ?, STATUS = ?, PAGES = ?, APPLIED_ON = ?," + " LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_PUBLICATION_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPublicationInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutablePublicationInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getTitle(), pMutable.getInterestGenre(),
        pMutable.getPublisherName(), pMutable.getDateOfPublication(), pMutable.getTypeId(), pMutable.getWebLink(),
        pMutable.getISSN(), pMutable.getIssue(), pMutable.getVolume(), pMutable.getJournalName(), pMutable.getCountry()
            .getId(), pMutable.getStatus(), pMutable.getPages(), pMutable.getAppliedOn(), pMutable.getActionTakenOn());
    return id;
  }

  @Override
  public PublicationInformation get(final Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public List<PublicationInformation> get(final String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY DATE_OF_PUBLICATION DESC ";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutablePublicationInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getTitle(), pMutable.getInterestGenre(), pMutable.getPublisherName(),
        pMutable.getDateOfPublication(), pMutable.getTypeId(), pMutable.getWebLink(), pMutable.getISSN(),
        pMutable.getIssue(), pMutable.getVolume(), pMutable.getJournalName(), pMutable.getCountry().getId(),
        pMutable.getStatus(), pMutable.getPages(), pMutable.getAppliedOn(), pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public int delete(MutablePublicationInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setId(resultSet.getLong("ID"));
      publicationInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      publicationInformation.setTitle(resultSet.getString("TITLE"));
      publicationInformation.setInterestGenre(resultSet.getString("INTEREST_GENRE"));
      publicationInformation.setPublisherName(resultSet.getString("PUBLISHER_NAME"));
      publicationInformation.setDateOfPublication(resultSet.getInt("DATE_OF_PUBLICATION"));
      publicationInformation.setTypeId(resultSet.getInt("TYPE"));
      publicationInformation.setWebLink(resultSet.getString("WEB_LINK"));
      publicationInformation.setISSN(resultSet.getString("ISSN"));
      publicationInformation.setIssue(resultSet.getString("ISSUE"));
      publicationInformation.setVolume(resultSet.getString("VOLUME"));
      publicationInformation.setJournalName(resultSet.getString("JOURNAL_NAME"));
      publicationInformation.setCountryId(resultSet.getInt("COUNTRY"));
      publicationInformation.setStatus(resultSet.getString("STATUS"));
      publicationInformation.setPages(resultSet.getString("PAGES"));
      publicationInformation.setAppliedOn(resultSet.getDate("APPLIED_ON"));
      publicationInformation.setActionTakenOn(resultSet.getDate("ACTION_TAKEN_ON"));
      publicationInformation.setLastModified(resultSet.getString("LAST_MODIFIED"));
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
      publicationInformation.setDateOfPublication(resultSet.getInt("date_of_publication"));
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
