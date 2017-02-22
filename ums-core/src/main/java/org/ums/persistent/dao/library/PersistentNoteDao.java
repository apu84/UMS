package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.ContributorDaoDecorator;
import org.ums.decorator.library.NoteDaoDecorator;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Note;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentNoteDao extends NoteDaoDecorator {
  static String SELECT_ALL = " SELECT  ID, MFN, NOTE,  VIEW_ORDER, LAST_MODIFIED FROM  Note";

  private JdbcTemplate mJdbcTemplate;

  public PersistentNoteDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Note get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentNoteDao.NoteRowMapper());
  }

  @Override
  public List<Note> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentNoteDao.NoteRowMapper());
  }

  class NoteRowMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {

      // PersistentCountry country = new PersistentCountry();
      // country.setId(resultSet.getInt("ID"));
      // country.setCode(resultSet.getString("CODE"));
      // country.setName(resultSet.getString("NAME"));
      // country.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Note> atomicReference = new AtomicReference<>(null);
      return atomicReference.get();
    }
  }

}
