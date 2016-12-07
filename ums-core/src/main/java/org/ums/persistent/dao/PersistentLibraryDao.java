package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.LibraryDaoDecorator;
import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.persistent.model.PersistentLibrary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by kawsu on 12/4/2016.
 */
public class PersistentLibraryDao extends LibraryDaoDecorator {
  static String INSERT_ALL = "INSERT INTO LIBRARY(BOOK_NAME, AUTHOR_NAME) VALUES(? , ?) ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentLibraryDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(MutableLibrary pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getBookName(), pMutable.getAuthorName());
  }

  class RoleRowMapper implements RowMapper<Library> {
    @Override
    public Library mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableLibrary library = new PersistentLibrary();
      library.setBookName(rs.getString("BOOK_NAME"));
      library.setAuthorName(rs.getString("AUTHOR_NAME"));
      AtomicReference<Library> atomicReference = new AtomicReference<>(library);
      return atomicReference.get();
    }
  }

}
