package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExpelledInformationDaoDecorator;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentExpelledInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public class PersistentExpelledInformationDao extends ExpelledInformationDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentExpelledInformationDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into EXPELLED_INFORMATION (ID,STUDENT_ID,SEMESTER_ID,COURSE_ID,EXAM_TYPE,REG_TYPE,EXPEL_REASON,INSERTED_ON) values (?,?,?,?,?,?,?,sysdate)";

  String SELECT_ALL =
      "SELECT STUDENT_ID,SEMESTER_ID,COURSE_ID,EXAM_TYPE,REG_TYPE,EXPEL_REASON,INSERTED_ON FROM EXPELLED_INFORMATION";

  @Override
  public List<ExpelledInformation> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new ExpelledInformationRowmapper());
  }

  @Override
  public Long create(MutableExpelledInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getStudentId(), pMutable.getSemesterId(), pMutable.getCourseId(),
        pMutable.getExamType(), pMutable.getRegType(), pMutable.getExpelledReason());
    return id;
  }

  class ExpelledInformationRowmapper implements RowMapper<ExpelledInformation> {
    @Override
    public ExpelledInformation mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentExpelledInformation application = new PersistentExpelledInformation();
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setCourseId(pResultSet.getString("COURSE_ID"));
      application.setExamType(pResultSet.getInt("EXAM_TYPE"));
      application.setRegType(pResultSet.getInt("REG_TYPE"));
      application.setExpelledReason(pResultSet.getString("EXPEL_REASON"));
      application.setInsertionDate(pResultSet.getString("INSERTED_ON"));
      return application;
    }
  }
}
