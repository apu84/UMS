package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionTotalSeatDaoDecorator;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.persistent.model.PersistentAdmissionTotalSeat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class PersistentAdmissionTotalSeatDao extends AdmissionTotalSeatDaoDecorator {

  String SELECT_ALL = "select * from admission_total_seat ";

  String INSERT_ONE =
      "insert into admission_total_seat (semester_id, program_id, program_type, total_seat, last_modified) "
          + "values (?,?,?,?," + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionTotalSeatDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<AdmissionTotalSeat> getAdmissionTotalSeat(int pSemesterId, ProgramType pProgramType) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID=? AND PROGRAM_TYPE=? order by program_id";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramType.getValue()},
        new AdmissionTotalSeatRowMapper());
  }

  @Override
  public int create(List<MutableAdmissionTotalSeat> pMutableList) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getAdmissionTotalSeatParams(pMutableList)).length;
  }

  @Override
  public int update(List<MutableAdmissionTotalSeat> pMutableList) {
    String query =
        "UPDATE ADMISSION_TOTAL_SEAT "
            + "SET TOTAL_SEAT = ?, last_modified ="
            + getLastModifiedSql()
            + " WHERE PROGRAM_TYPE = ? AND PROGRAM_ID = ? AND SEMESTER_ID IN (SELECT SEMESTER_ID "
            + "                                                              FROM MST_SEMESTER "
            + "                                                              WHERE SEMESTER_ID = ? AND STATUS = 2)";
    return mJdbcTemplate.batchUpdate(query, getAdmissionTotalSeatUpdateParams(pMutableList)).length;
  }

  private List<Object[]> getAdmissionTotalSeatParams(List<MutableAdmissionTotalSeat> pSeats) {

    List<Object[]> params = new ArrayList<>();

    for(AdmissionTotalSeat seat : pSeats) {
      params.add(new Object[] {seat.getSemester().getId(), seat.getProgram().getId(),
          seat.getProgramType().getValue(), seat.getTotalSeat()});
    }
    return params;
  }

  private List<Object[]> getAdmissionTotalSeatUpdateParams(List<MutableAdmissionTotalSeat> pSeats) {

    List<Object[]> params = new ArrayList<>();

    for(AdmissionTotalSeat seat : pSeats) {
      params.add(new Object[] {seat.getTotalSeat(), seat.getProgramType().getValue(),
          seat.getProgram().getId(), seat.getSemester().getId()});
    }
    return params;
  }

  class AdmissionTotalSeatRowMapper implements RowMapper<AdmissionTotalSeat> {

    @Override
    public AdmissionTotalSeat mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionTotalSeat seat = new PersistentAdmissionTotalSeat();
      seat.setId(pResultSet.getInt("id"));
      seat.setSemesterId(pResultSet.getInt("semester_id"));
      seat.setProgramId(pResultSet.getInt("program_id"));
      seat.setTotalSeat(pResultSet.getInt("total_seat"));
      seat.setLastModified(pResultSet.getString("last_modified"));
      seat.setProgramType(ProgramType.get(pResultSet.getInt("program_type")));
      return seat;
    }
  }

}
