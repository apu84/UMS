package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.LibraryDaoDecorator;
import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.persistent.model.PersistentLibrary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentLibraryDao extends LibraryDaoDecorator {
  static String INSERT_ALL = "INSERT INTO LIBRARY(BOOK_NAME, AUTHOR_NAME) VALUES(? , ?) ";
  static String SELECT_ALL = "SELECT BOOK_NAME,AUTHOR_NAME FROM LIBRARY";
  static String DELETE_ALL = "DELETE FROM LIBRARY";
  static String SELECT_ALL_BOOKS = "SELECT BOOK_NAME,AUTHOR_NAME FROM LIBRARY";

  private JdbcTemplate mJdbcTemplate;

  public PersistentLibraryDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(MutableLibrary pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getBookName(), pMutable.getAuthorName());
  }

  public List<Library> getAllTheLibraryBooks(String pBook) {
    String query = SELECT_ALL + " WHERE BOOK_NAME=?";
    return mJdbcTemplate.query(query, new Object[] {pBook},
        new PersistentLibraryDao.RoleRowMapper());
  }

  public int deleteByBookNameAndAuthorName(String pBookName, String pAuthorName) {
    String query = DELETE_ALL + " WHERE BOOK_NAME=? AND AUTHOR_NAME=?";
    return mJdbcTemplate.update(query, pBookName, pAuthorName);
  }

  public List<Library> getAllLibraryBooks() {
    String query = SELECT_ALL_BOOKS;
    return mJdbcTemplate.query(query, new RoleRowMapper());
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
