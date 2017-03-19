package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.NoteDaoDecorator;
import org.ums.decorator.library.SubjectDaoDecorator;
import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.immutable.library.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentSubjectDao extends SubjectDaoDecorator {
  static String SELECT_ALL = " SELECT  ID, MFN, SUBJECT,  VIEW_ORDER, LAST_MODIFIED FROM  SUBJECT";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSubjectDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Subject get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentSubjectDao.SubjectRowMapper());
  }

  @Override
  public List<Subject> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentSubjectDao.SubjectRowMapper());
  }

  class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {

      // PersistentCountry country = new PersistentCountry();
      // country.setId(resultSet.getInt("ID"));
      // country.setCode(resultSet.getString("CODE"));
      // country.setName(resultSet.getString("NAME"));
      // country.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Subject> atomicReference = new AtomicReference<>(null);
      return atomicReference.get();
    }
  }

}
