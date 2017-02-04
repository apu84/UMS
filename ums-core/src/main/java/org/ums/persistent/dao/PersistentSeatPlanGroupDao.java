package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanGroupDaoDecorator;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.persistent.model.PersistentSeatPlanGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentSeatPlanGroupDao extends SeatPlanGroupDaoDecorator {

  String SELECT_ALL =
      "SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,PROGRAM_SHORT_NAME,TOTAL_STUDENT,LAST_UPDATED FROM SP_GROUP ";
  String INSERT_ONE =
      "INSERT INTO SP_GROUP (SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,LAST_UPDATED,TYPE,PROGRAM_SHORT_NAME,TOTAL_STUDENT) VALUES(?,?,?,?,?, systimestamp,?,?,?)";
  String UPDATE_ONE =
      "UPDATE SP_GROUP SET SEMESTER_ID=?,PROGRAM_ID=?,YEAR=?, SEMESTER=?, GROUP_NO=?, LAST_UPDATED = systimestamp, TYPE=?,PROGRAM_SHORT_NAME=?,TOTAL_STUDENT=?";
  String DELETE_ONE = "DELETE  FROM SP_GROUP";
  String SELECT_ALL_FROM_EXAM_ROUTINE =
      " select tmp5.*,program_short_name from "
          + "             (      "
          + "             select group_name,tmp4.program_id,tmp4.semester,tmp4.exam_type,course_year,course_semester,count(student_id) total_student from      "
          + "             (      "
          + "             select tmp3.*,students.student_id from "
          + "             (select distinct semester,exam_type,program_id,course_year,course_semester,group_name from      "
          + "             (select * from       "
          + "             (Select EXAM_ROUTINE.*,YEAR COURSE_YEAR,MST_COURSE.SEMESTER COURSE_SEMESTER From EXAM_ROUTINE,MST_COURSE      "
          + "             Where EXAM_ROUTINE.COURSE_ID=MST_COURSE.COURSE_ID and EXAM_ROUTINE.EXAM_TYPE=? and EXAM_ROUTINE.SEMESTER=?     "
          + "             order by exam_date      "
          + "             ) tmp1,      "
          + "             (select exam_date,      "
          + "             CASE WHEN mod(ind,3)=0 THEN 3 ELSE mod(ind,3) END AS Group_name      "
          + "             from (      "
          + "             select tmp.exam_date,tmp.exam_type,rownum ind from (      "
          + "             Select Distinct Exam_Date,Exam_type From EXAM_ROUTINE  where exam_date not in "
          + "                           ( select exclude_date from sp_parameter where exam_type=? and semester_id=?)  Order by Exam_Date "
          + "             ) tmp)       "
          + "             )tmp2      "
          + "             where tmp1.exam_date = tmp2.exam_date )      "
          + "             order by group_name      "
          + "             )tmp3, students,(select distinct(STUDENT_ID) from UG_REGISTRATION_RESULT) ugRegistrationResult "
          + "             where "
          + "             STUDENTS.STUDENT_ID = ugRegistrationResult.STUDENT_ID and "
          + "             tmp3.program_id =  students.program_id "
          + "             and tmp3.course_year =  students.CURR_YEAR "
          + "             and tmp3.course_semester =  students.CURR_SEMESTER "
          + "             )tmp4 group by group_name,      "
          + "             tmp4.program_id,tmp4.semester,tmp4.exam_type,course_year,course_semester      "
          + "             )tmp5,mst_program      "
          + "             where tmp5.program_id = mst_program.program_id      "
          + "             order by group_name,program_short_name,course_year,course_semester";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanGroupDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId, int pExamType) {
    String query = SELECT_ALL_FROM_EXAM_ROUTINE;
    return mJdbcTemplate.query(query,
        new Object[] {pExamType, pSemesterId, pExamType, pSemesterId},
        new SeatPlanGroupRowmapperTemp());
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemesterTypeFromDb(int pSemesterId, int pExamType) {
    String query =
        SELECT_ALL + " WHERE SEMESTER_ID=? AND TYPE=? order by group_no,program_id,year,semester";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType},
        new SeatPlanGroupRowmapper());
  }

  @Override
  public int deleteBySemesterAndExamType(int pSemesterId, int pExamType) {
    String query = DELETE_ONE + " WHERE SEMESTER_ID=?  AND TYPE=?";
    return mJdbcTemplate.update(query, pSemesterId, pExamType);
  }

  @Override
  public List<SeatPlanGroup> getBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    String query =
        SELECT_ALL
            + " WHERE SEMESTER_ID=? AND GROUP_NO=? AND TYPE=? ORDER BY GROUP_NO,PROGRAM_ID,YEAR,SEMESTER ASC";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pGroupNo, pType},
        new SeatPlanGroupRowmapper());
  }

  @Override
  public List<SeatPlanGroup> getAll() {
    String query = SELECT_ALL + " ORDER BY GROUP_NO,PROGRAM_ID,YEAR,SEMESTER ASC";
    return mJdbcTemplate.query(query, new SeatPlanGroupRowmapper());
  }

  @Override
  public SeatPlanGroup get(Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new SeatPlanGroupRowmapper());
  }

  @Override
  public int update(MutableSeatPlanGroup pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableSeatPlanGroup> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableSeatPlanGroup pMutable) {
    String query = DELETE_ONE + " WHERE SEMESTER_ID=?";
    return mJdbcTemplate.update(query, pMutable.getSemester().getId());
  }

  @Override
  public int createSeatPlanGroup(int pSemesterid, int pExamType) {
    String query =
        "INSERT INTO sp_group (semester_id, program_id, year, semester, group_no, type, total_student, program_short_name) "
            + "SELECT "
            + "  r.semester_id, "
            + "  r.program_id, "
            + "  r.year, "
            + "  r.semester, "
            + "  e.group_no, "
            + "  e.EXAM_TYPE AS type, "
            + "  r.TOTAL_STUDENT, "
            + "  e.program_short_name "
            + "FROM ( "
            + " "
            + "       SELECT "
            + "         s.semester_id, "
            + "         s.PROGRAM_ID, "
            + "         s.year, "
            + "         s.semester, "
            + "         count(STUDENT_ID) AS total_student "
            + "       FROM ( "
            + "              SELECT DISTINCT "
            + "                s.student_id, "
            + "                s.PROGRAM_ID, "
            + "                r.semester_id, "
            + "                s.CURR_YEAR     AS year, "
            + "                s.CURR_SEMESTER AS semester "
            + "              FROM STUDENTS s, UG_REGISTRATION_RESULT r, exam_routine er "
            + "              WHERE s.STUDENT_ID = r.STUDENT_ID AND "
            + "                    r.SEMESTER_ID = ? AND r.EXAM_TYPE = ? and r.semester_id=er.semester and r.course_id=er.course_id) s "
            + "       GROUP BY s.SEMESTER_ID, s.PROGRAM_ID, s.year, s.semester "
            + "       ORDER BY PROGRAM_ID, year, semester) r, "
            + " "
            + "  ( "
            + "    SELECT "
            + "      DISTINCT "
            + "      EXAM_ROUTINE.PROGRAM_ID, "
            + "      year, "
            + "      MST_COURSE.SEMESTER, "
            + "      program_short_name, "
            + "      exam_group            AS group_no, "
            + "      EXAM_ROUTINE.semester AS semester_id, "
            + "      exam_type "
            + "    FROM EXAM_ROUTINE, MST_PROGRAM, MST_COURSE "
            + "    WHERE Exam_routine.SEMESTER = ? AND EXAM_TYPE = ? AND EXAM_ROUTINE.PROGRAM_ID = MST_PROGRAM.PROGRAM_ID AND "
            + "          EXAM_ROUTINE.COURSE_ID = MST_COURSE.COURSE_ID AND Mst_course.SEMESTER > 0 AND "
            + "          exam_date IN (SELECT DISTINCT exam_date "
            + "                        FROM EXAM_ROUTINE "
            + "                        WHERE SEMESTER = ? AND EXAM_TYPE = ? "
            + "                        GROUP BY EXAM_DATE "
            + "                        HAVING count(EXAM_DATE) > 2) "
            + "    ORDER BY exam_routine.exam_group, EXAM_ROUTINE.PROGRAM_ID, MST_COURSE.YEAR, MST_COURSE.SEMESTER) e "
            + "WHERE r.SEMESTER_ID = e.semester_id AND r.PROGRAM_ID = e.PROGRAM_ID AND r.year = e.YEAR AND r.semester = e.semester "
            + "ORDER BY e.group_no, e.PROGRAM_ID, e.YEAR, e.SEMESTER";
    return mJdbcTemplate.update(query, new Object[] {pSemesterid, pExamType, pSemesterid,
        pExamType, pSemesterid, pExamType});
  }

  @Override
  public int delete(List<MutableSeatPlanGroup> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public int checkSeatPlanGroupDataSize(int pSemesterId, int pExamType) {
    String query = "SELECT  COUNT(*) FROM SP_GROUP WHERE SEMESTER_ID=? AND TYPE=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pExamType);
  }

  @Override
  public int create(MutableSeatPlanGroup pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getProgram()
        .getId(), pMutable.getAcademicYear(), pMutable.getAcademicSemester(),
        pMutable.getGroupNo(), pMutable.getExamType());
  }

  @Override
  public int create(List<MutableSeatPlanGroup> pMutableList) {
    return mJdbcTemplate.batchUpdate(INSERT_ONE, getInsertParamList(pMutableList)).length;
  }

  private List<Object[]> getInsertParamList(List<MutableSeatPlanGroup> pSeatPlanGroups) {
    List<Object[]> params = new ArrayList<>();
    for(SeatPlanGroup seatPlanGroup : pSeatPlanGroups) {
      params.add(new Object[] {
          // seatPlanGroup.getId(),
          seatPlanGroup.getSemester().getId(), seatPlanGroup.getProgram().getId(),
          seatPlanGroup.getAcademicYear(), seatPlanGroup.getAcademicSemester(),
          seatPlanGroup.getGroupNo(), seatPlanGroup.getExamType(), seatPlanGroup.getProgramName(),
          seatPlanGroup.getTotalStudentNumber(),});
    }

    return params;
  }

  class SeatPlanGroupRowmapperTemp implements RowMapper<SeatPlanGroup> {
    @Override
    public SeatPlanGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlanGroup seatPlanGroup = new PersistentSeatPlanGroup();
      seatPlanGroup.setGroupNo(pResultSet.getInt("GROUP_NAME"));
      seatPlanGroup.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      seatPlanGroup.setSemesterId(pResultSet.getInt("SEMESTER"));
      seatPlanGroup.setExamType(pResultSet.getInt("EXAM_TYPE")); // SELECT
                                                                 // ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,TO_CHAR(LAST_UPDATED,'DD/MM/YYYY'
      seatPlanGroup.setAcademicYear(pResultSet.getInt("COURSE_YEAR"));
      seatPlanGroup.setAcademicSemester(pResultSet.getInt("COURSE_SEMESTER"));
      seatPlanGroup.setTotalStudentNumber(pResultSet.getInt("TOTAL_STUDENT"));
      seatPlanGroup.setProgramShortName(pResultSet.getString("PROGRAM_SHORT_NAME"));
      // seatPlanGroup.setLastUpdateDate(pResultSet.getString("LAST_UPDATED"));
      return seatPlanGroup;
    }
  }

  class SeatPlanGroupRowmapper implements RowMapper<SeatPlanGroup> {
    @Override
    public SeatPlanGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlanGroup seatPlanGroup = new PersistentSeatPlanGroup();
      seatPlanGroup.setId(pResultSet.getInt("ID"));
      seatPlanGroup.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      seatPlanGroup.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      seatPlanGroup.setAcademicYear(pResultSet.getInt("YEAR"));
      seatPlanGroup.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      seatPlanGroup.setGroupNo(pResultSet.getInt("GROUP_NO"));
      seatPlanGroup.setLastUpdateDate(pResultSet.getString("LAST_UPDATED"));
      seatPlanGroup.setExamType(pResultSet.getInt("TYPE")); // SELECT
                                                            // ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,TO_CHAR(LAST_UPDATED,'DD/MM/YYYY'
      seatPlanGroup.setProgramShortName(pResultSet.getString("PROGRAM_SHORT_NAME"));
      seatPlanGroup.setTotalStudentNumber(pResultSet.getInt("TOTAL_STUDENT"));
      return seatPlanGroup;
    }
  }

}
