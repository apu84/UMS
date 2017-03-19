package org.ums.persistent.dao.library;

import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.CourseDaoDecorator;
import org.ums.decorator.library.AuthorDaoDecorator;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Author;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;
import org.ums.persistent.dao.PersistentCourseDao;
import org.ums.persistent.model.PersistentCourse;
import org.ums.persistent.model.library.PersistentAuthor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 30-Jan-17.
 */
public class PersistentAuthorDao extends AuthorDaoDecorator {
  static String SELECT_ALL =
      "Select ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,SHORT_NAME,GENDER,ADDRESS,COUNTRY,LAST_MODIFIED From MST_AUTHOR ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAuthorDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Author get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentAuthorDao.AuthorRowMapper());
  }

  @Override
  public List<Author> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentAuthorDao.AuthorRowMapper());
  }

  class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAuthor author = new PersistentAuthor();
      author.setId(resultSet.getInt("ID"));
      author.setFirstName(resultSet.getString("FIRST_NAME"));
      author.setMiddleName(resultSet.getString("MIDDLE_NAME"));
      author.setLastName(resultSet.getString("LAST_NAME"));
      author.setShortName(resultSet.getString("SHORT_NAME"));
      author.setGender(resultSet.getString("GENDER"));
      author.setAddress(resultSet.getString("ADDRESS"));
      author.setCountryId(resultSet.getInt("COUNTRY"));
      author.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Author> atomicReference = new AtomicReference<>(author);
      return atomicReference.get();
    }
  }

}
