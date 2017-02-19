package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ProgramTypeDaoDecorator;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.persistent.model.PersistentProgramType;

public class PersistentProgramTypeDao extends ProgramTypeDaoDecorator {
  static String SELECT_ALL = "SELECT TYPE_ID, TYPE_NAME FROM MST_PROGRAM_TYPE ";
  static String UPDATE_ONE = "UPDATE MST_PROGRAM_TYPE SET TYPE_NAME = ? ";
  static String DELETE_ONE = "DELETE FROM MST_PROGRAM_TYPE ";
  static String INSERT_ONE = "INSERT INTO MST_PROGRAM_TYPE(TYPE_ID, TYPE_NAME) VALUES(?, ?) ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentProgramTypeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public ProgramType get(final Integer pId) {
    String query = SELECT_ALL + "WHERE TYPE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ProgramTypeRowMapper());
  }

  public List<ProgramType> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ProgramTypeRowMapper());
  }

  public int update(final MutableProgramType pProgramType) {
    String query = UPDATE_ONE + "WHERE TYPE_ID = ?";
    return mJdbcTemplate.update(query, pProgramType.getName(), pProgramType.getId());
  }

  public int delete(final MutableProgramType pProgramType) {
    String query = DELETE_ONE + "WHERE TYPE_ID = ?";
    return mJdbcTemplate.update(query, pProgramType.getId());
  }

  public Integer create(final MutableProgramType pProgramType) {
    mJdbcTemplate.update(INSERT_ONE, pProgramType.getId(), pProgramType.getName());
    return pProgramType.getId();
  }

  class ProgramTypeRowMapper implements RowMapper<ProgramType> {
    @Override
    public ProgramType mapRow(ResultSet resultSet, int i) {
      try {
        PersistentProgramType programType = new PersistentProgramType();
        programType.setId(resultSet.getInt("TYPE_ID"));
        programType.setName(resultSet.getString("TYPE_NAME"));
        AtomicReference<ProgramType> atomicReference = new AtomicReference<>(programType);
        return atomicReference.get();
      } catch(SQLException sqlE) {
        throw new RuntimeException(sqlE);
      }
    }
  }
}
