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
      "INSERT INTO EMP_PUBLICATION_INFO (EMPLOYEE_ID, PUBLICATION_TITLE, INTEREST_GENRE, PUBLISHER_NAME, DATE_OF_PUBLICATION, PUBLICATION_TYPE, PUBLICATION_WEB_LINK) VALUES (? ,? ,?, ?, TO_DATE(?, 'DD/MM/YYYY') , ?, ?)";

  static String GET_ONE =
      "Select EMPLOYEE_ID, PUBLICATION_TITLE, INTEREST_GENRE, PUBLISHER_NAME, DATE_OF_PUBLICATION, PUBLICATION_TYPE, PUBLICATION_WEB_LINK From EMP_PUBLICATION_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_PUBLICATION_INFO";

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
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeePublicationInformationParams(
      List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation publicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {publicationInformation.getEmployeeId(), publicationInformation.getPublicationTitle(),
          publicationInformation.getInterestGenre(), publicationInformation.getPublisherName(),
          publicationInformation.getDateOfPublication(), publicationInformation.getPublicationType(),
          publicationInformation.getPublicationWebLink()});

    }
    return params;
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate.query(query, new Object[] {employeeId},
        new PersistentPublicationInformationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setEmployeeId(resultSet.getString("employee_id"));
      publicationInformation.setPublicationTitle(resultSet.getString("publication_title"));
      publicationInformation.setInterestGenre(resultSet.getString("interest_genre"));
      publicationInformation.setPublisherName(resultSet.getString("publisher_name"));
      publicationInformation.setDateOfPublication(resultSet.getString("date_of_publication"));
      publicationInformation.setPublicationType(resultSet.getString("publication_type"));
      publicationInformation.setPublicationWebLink(resultSet.getString("publication_web_link"));
      return publicationInformation;
    }
  }
}
