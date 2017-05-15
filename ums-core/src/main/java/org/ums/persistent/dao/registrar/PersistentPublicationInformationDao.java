package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.PublicationInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.persistent.model.registrar.PersistentPublicationInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentPublicationInformationDao extends PublicationInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_PUBLICATION_INFO (EMPLOYEE_ID, PUBLICATION_TITLE, INTEREST_GENRE, PUBLISHER_NAME, DATE_OF_PUBLICATION, PUBLICATION_TYPE, PUBLICATION_WEB_LINK, PUBLICATION_ISSN, PUBLICATION_ISSUE, PUBLICATION_VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PUBLICATION_PAGES) VALUES (? ,? ,?, ?, TO_DATE(?, 'DD/MM/YYYY') , ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, PUBLICATION_TITLE, INTEREST_GENRE, PUBLISHER_NAME, to_char(DATE_OF_PUBLICATION,'dd/mm/yyyy') DATE_OF_PUBLICATION, PUBLICATION_TYPE, PUBLICATION_WEB_LINK, PUBLICATION_ISSN, PUBLICATION_ISSUE, PUBLICATION_VOLUME, JOURNAL_NAME, COUNTRY, STATUS, PUBLICATION_PAGES From EMP_PUBLICATION_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_PUBLICATION_INFO";

  static String UPDATE_STATUS = "UPDATE EMP_PUBLICATION_INFO SET STATUS=? ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPublicationInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int savePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeePublicationInformationParams(pMutablePublicationInformation)).length;
  }

  @Override
  public int deletePublicationInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ? AND STATUS = '2' ";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  @Override
  public int updatePublicationStatus(MutablePublicationInformation pMutablePublicationInformation) {
    String query = UPDATE_STATUS + "WHERE ID=?";
    return mJdbcTemplate.update(query, pMutablePublicationInformation.getPublicationStatus(),
        pMutablePublicationInformation.getRowId());
  }

  private List<Object[]> getEmployeePublicationInformationParams(
      List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation publicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {publicationInformation.getEmployeeId(), publicationInformation.getPublicationTitle(),
          publicationInformation.getInterestGenre(), publicationInformation.getPublisherName(),
          publicationInformation.getDateOfPublication(), publicationInformation.getPublicationType(),
          publicationInformation.getPublicationWebLink(), publicationInformation.getPublicationISSN(),
          publicationInformation.getPublicationIssue(), publicationInformation.getPublicationVolume(),
          publicationInformation.getPublicationJournalName(), publicationInformation.getPublicationCountry(),
          publicationInformation.getPublicationStatus(), publicationInformation.getPublicationPages()});

    }
    return params;
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate.query(query, new Object[] {employeeId},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  @Override
  public List<PublicationInformation> getPublicationInformation(final String pPublicationStatus) {
    String query = GET_ONE + " Where status = ?";
    return mJdbcTemplate.query(query, new Object[] {pPublicationStatus},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setRowId(resultSet.getInt("id"));
      publicationInformation.setEmployeeId(resultSet.getString("employee_id"));
      publicationInformation.setPublicationTitle(resultSet.getString("publication_title"));
      publicationInformation.setInterestGenre(resultSet.getString("interest_genre"));
      publicationInformation.setPublisherName(resultSet.getString("publisher_name"));
      publicationInformation.setDateOfPublication(resultSet.getString("date_of_publication"));
      publicationInformation.setPublicationType(resultSet.getString("publication_type"));
      publicationInformation.setPublicationWebLink(resultSet.getString("publication_web_link"));
      publicationInformation.setPublicationISSN(resultSet.getString("publication_issn"));
      publicationInformation.setPublicationIssue(resultSet.getString("publication_issue"));
      publicationInformation.setPublicationVolume(resultSet.getString("publication_volume"));
      publicationInformation.setPublicationJournalName(resultSet.getString("journal_name"));
      publicationInformation.setPublicationCountry(resultSet.getString("country"));
      publicationInformation.setPublicationStatus(resultSet.getString("status"));
      publicationInformation.setPublicationPages(resultSet.getString("publication_pages"));
      return publicationInformation;
    }
  }
}
