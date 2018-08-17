package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.StudentsExamAttendantInfoDaoDecorator;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentStudentsExamAttendantInfo;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public class PersistentStudentsExamAttendantInfoDao extends StudentsExamAttendantInfoDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentStudentsExamAttendantInfoDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_STUDENT_EXAM_ATTENDANT_INFO =
      "MERGE INTO DER_EXAM_ATTENDANT_INFO D  "
          + "using (select 1 from DUAL) s  "
          + "      on (D.PROGRAM_ID=? and D.SEMESTER_ID=? and D.YEAR=? and D.SEMESTER=? and D.EXAM_TYPE=? and D.EXAM_DATE = TO_DATE(?,'DD-MM-YYYY')  "
          + " )  "
          + "   WHEN MATCHED THEN UPDATE SET D.ABSENT_STUDENT=?  "
          + "   WHEN NOT MATCHED THEN  "
          + "   INSERT (ID,PROGRAM_ID,SEMESTER_ID,YEAR,SEMESTER,EXAM_TYPE,PRESENT_STUDENT,ABSENT_STUDENT,REGISTERED_STUDENT,EXAM_DATE)  "
          + "     VALUES (?,?,?,?,?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'))";

  String EXAM_ATTENDANT_INFO =
      "SELECT PROGRAM_ID,SEMESTER_ID,\"YEAR\",SEMESTER,EXAM_TYPE,PRESENT_STUDENT,ABSENT_STUDENT,REGISTERED_STUDENT,to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE FROM DER_EXAM_ATTENDANT_INFO WHERE  SEMESTER_ID=? AND EXAM_TYPE=? "
          + " AND EXAM_DATE = TO_DATE(?,'DD-MM-YYYY')";

  @Override
  public List<StudentsExamAttendantInfo> getSemesterExamTypeDateWiseRecords(Integer pSemesterId, Integer pExamType,
      String pExamDate) {

    return mJdbcTemplate.query(EXAM_ATTENDANT_INFO, new Object[] {pSemesterId, pExamType, pExamDate},
        new ExamAttendantRowMapper());
  }

  @Override
  public List<Long> create(List<MutableStudentsExamAttendantInfo> pMutableList) {
    List<Object[]> parameters = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_STUDENT_EXAM_ATTENDANT_INFO, parameters);
    return null;
  }

  private List<Object[]> getInsertParamList(List<MutableStudentsExamAttendantInfo> pMutableApplicationCCIs) {

    List<Object[]> params = new ArrayList<>();
    try {
      for(MutableStudentsExamAttendantInfo app : pMutableApplicationCCIs) {
        params.add(new Object[] {app.getProgramId(), app.getSemesterId(), app.getYear(), app.getSemester(),
            app.getExamType(), app.getExamDate(), app.getAbsentStudents(), mIdGenerator.getNumericId(),
            app.getProgramId(), app.getSemesterId(), app.getYear(), app.getSemester(), app.getExamType(),
            app.getRegisteredStudents() - app.getAbsentStudents(), app.getAbsentStudents(),
            app.getRegisteredStudents(), app.getExamDate()});
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return params;
  }
}


class ExamAttendantRowMapper implements RowMapper<StudentsExamAttendantInfo> {

  @Override
  public StudentsExamAttendantInfo mapRow(ResultSet pResultSet, int i) throws SQLException {
    PersistentStudentsExamAttendantInfo application = new PersistentStudentsExamAttendantInfo();
    application.setProgramId(pResultSet.getInt("PROGRAM_ID"));
    application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
    application.setYear(pResultSet.getInt("YEAR"));
    application.setSemester(pResultSet.getInt("SEMESTER"));
    application.setExamType(pResultSet.getInt("EXAM_TYPE"));
    application.setPresentStudents(pResultSet.getInt("PRESENT_STUDENT"));
    application.setAbsentStudents(pResultSet.getInt("ABSENT_STUDENT"));
    application.setRegisteredStudents(pResultSet.getInt("REGISTERED_STUDENT"));
    application.setExamDate(pResultSet.getString("EXAM_DATE"));
    return application;
  }
}
