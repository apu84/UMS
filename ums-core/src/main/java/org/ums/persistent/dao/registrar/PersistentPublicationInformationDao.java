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
      "INSERT INTO EMP_PUBLICATION_INFO (EMPLOYEE_ID, PUBLICATION_TITLE, INTEREST_GENRE, AUTHOR, PUBLISHER_NAME, DATE_OF_PUBLICATION, PUBLICATION_TYPE, PUBLICATION_WEB_LINK) VALUES (? ,? ,?, ?, ?, TO_DATE(?, 'DD/MM/YYYY') , ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPublicationInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int savePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeePublicationInformationParams(pMutablePublicationInformation)).length;
  }

  private List<Object[]> getEmployeePublicationInformationParams(
      List<MutablePublicationInformation> pMutablePublicationInformation) {
    List<Object[]> params = new ArrayList<>();
    for(PublicationInformation publicationInformation : pMutablePublicationInformation) {
      params.add(new Object[] {publicationInformation.getEmployeeId(), publicationInformation.getPublicationTitle(),
          publicationInformation.getInterestGenre(), publicationInformation.getAuthor(),
          publicationInformation.getPublisherName(), publicationInformation.getDateOfPublication(),
          publicationInformation.getPublicationType(), publicationInformation.getPublicationWebLink()});

    }
    return params;
  }

  class RoleRowMapper implements RowMapper<PublicationInformation> {

    @Override
    public PublicationInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      publicationInformation.setEmployeeId(resultSet.getInt("employee_id"));
      publicationInformation.setPublicationTitle(resultSet.getString("publication_title"));
      publicationInformation.setInterestGenre(resultSet.getString("interest_genre"));
      publicationInformation.setAuthor(resultSet.getString("author"));
      publicationInformation.setPublisherName(resultSet.getString("publisher_name"));
      publicationInformation.setDateOfPublication(resultSet.getString("date_of_publication"));
      publicationInformation.setPublicationType(resultSet.getString("publication_type"));
      publicationInformation.setPublicationWebLink(resultSet.getString("publication_web_link"));
      return publicationInformation;
    }
  }
}
