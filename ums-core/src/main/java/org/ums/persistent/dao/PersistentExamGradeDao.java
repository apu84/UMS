package org.ums.persistent.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.CourseTeacherDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.Course;
import org.ums.enums.CourseMarksSubmissionStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ikh on 4/29/2016.
 */
public class PersistentExamGradeDao  extends ExamGradeDaoDecorator {

    static String SELECT_THEORY_MARKS = "Select * From UG_THEORY_MARKS  Where Semester_Id='11012016' and Course_Id='CID1' and Exam_Type=1 ";
    static String SELECT_PART_INFO="Select * From MARKS_SUBMISSION_STATUS Where Semester_Id='11012016' and Course_Id='CID1' and Exam_Type=1 ";

    static String UPDATE_THEORY_MARKS="Update  UG_THEORY_MARKS Set Quiz=?,Class_Performance=?,Part_A=?,Part_B=?,Total=?,Grade_Letter=?,Grade_Point=?,Status=? " +
            " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=?";

    static String SELECT_GRADE_SUBMISSION_TABLE="Select tmp5.*,Status From ( " +
            "Select tmp4.*,MVIEW_TEACHERS.TEACHER_NAME Scrutinizer_Name,getCourseTeacher(semester_id,course_id) Course_Teachers From " +
            "( " +
            "Select tmp3.*,MVIEW_TEACHERS.TEACHER_NAME Preparer_name From  " +
            "( " +
            "Select tmp2.*,Mst_Course.Course_Title,Program_Short_Name,MST_COURSE.COURSE_NO,Year,Semester from ( " +
            "Select semester_id,course_id,preparer preparer_id,scrutinizer scrutinizer_id From PREPARER_SCRUTINIZER Where Semester_Id=11012016 And (Preparer=28 or Scrutinizer=28) " +
            "Union " +
            "Select tmp1.semester_id,tmp1.course_id,preparer preparer_id,scrutinizer scrutinizer_id from ( " +
            "Select Semester_Id,Course_Id From COURSE_TEACHER Where Semester_Id=11012016 and Teacher_Id=28)tmp1,PREPARER_SCRUTINIZER " +
            "Where tmp1.semester_id=PREPARER_SCRUTINIZER.semester_id(+) " +
            "and tmp1.course_id=PREPARER_SCRUTINIZER.course_id(+))tmp2,Mst_Course,Mst_Syllabus,Mst_Program " +
            "Where tmp2.Course_id=Mst_Course.Course_id " +
            "And MST_COURSE.SYLLABUS_ID=MST_SYLLABUS.SYLLABUS_ID " +
            "And MST_PROGRAM.PROGRAM_ID=MST_SYLLABUS.PROGRAM_ID " +
            ")tmp3,MVIEW_TEACHERS " +
            "Where tmp3.preparer_id=MVIEW_TEACHERS.teacher_id (+))tmp4,MVIEW_TEACHERS " +
            "Where tmp4.scrutinizer_id=MVIEW_TEACHERS.teacher_id (+) " +
            ")tmp5, Marks_Submission_Status " +
            "Where tmp5.course_id=MARKS_SUBMISSION_STATUS.COURSE_ID(+) " +
            "And tmp5.SEMESTER_ID=MARKS_SUBMISSION_STATUS.SEMESTER_ID(+) ";


    private JdbcTemplate mJdbcTemplate;

    public PersistentExamGradeDao(final JdbcTemplate pJdbcTemplate) {
        mJdbcTemplate = pJdbcTemplate;
    }

    @Override
    public List<StudentGradeDto> getAllGradeForTheoryCourse(int pSemesterId,String pCourseId,int pExamType) throws Exception {
        String ORDER_BY=  " Order by Student_Id,Status  ";
        String query = SELECT_THEORY_MARKS + ORDER_BY;
        return mJdbcTemplate.query(query, new StudentMarksRowMapper());
    }

    @Override
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int pSemesterId,String pCourseId,int pExamType) throws Exception {
        String query = SELECT_PART_INFO;
        return mJdbcTemplate.queryForObject(query, new MarksSubmissionStatusRowMapper());
    }

    @Override
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int pSemesterId,int pExamType) throws Exception {
        String query = SELECT_GRADE_SUBMISSION_TABLE;
        return mJdbcTemplate.query(query, new MarksSubmissionStatusTableRowMapper());
    }

    class StudentMarksRowMapper implements RowMapper<StudentGradeDto> {
        @Override
        public StudentGradeDto mapRow(ResultSet resultSet, int i) throws SQLException {
            StudentGradeDto marks = new StudentGradeDto();
            marks.setStudentId(resultSet.getString("STUDENT_ID"));
            //marks.setStudentName(resultSet.getString("STUDENT_NAME"));
            marks.setStudentName("Md. Abul Kalam Azad");

            float quiz = resultSet.getFloat("QUIZ");
            marks.setQuiz(resultSet.wasNull() ? null : quiz);

            float classPerformance = resultSet.getFloat("CLASS_PERFORMANCE");
            marks.setClassPerformance(resultSet.wasNull() ? null : classPerformance);

            float partA = resultSet.getFloat("PART_A");
            marks.setPartA(resultSet.wasNull() ? null : partA);

            float partB = resultSet.getFloat("PART_B");
            marks.setPartB(resultSet.wasNull() ? null : partB);

            marks.setPartTotal(resultSet.getFloat("PART_TOTAL"));

            float total = resultSet.getFloat("TOTAL");
            marks.setTotal(resultSet.wasNull() ? null : total);

            marks.setGradePoint(resultSet.getFloat("GRADE_POINT"));
            marks.setGradeLetter(resultSet.getString("GRADE_LETTER"));
            //marks.setStatus(resultSet.getFloat("STATUS"));

            AtomicReference<StudentGradeDto> atomicReference = new AtomicReference<>(marks);
            return atomicReference.get();
        }
    }

    public boolean saveGradeSheet(int pSemesterId,String pCourseId,int pExamType,List<StudentGradeDto> pGradeList) throws Exception {
        batchInsertApplicationCourse(pSemesterId, pCourseId, pExamType, pGradeList);
        return true;
    }

    public void batchInsertApplicationCourse(int pSemesterId,String pCourseId,int pExamType,List<StudentGradeDto> pGradeList){
        String sql = UPDATE_THEORY_MARKS;

        mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {


            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = pGradeList.get(i);
                if (gradeDto.getQuiz() == -1)
                    ps.setNull(1, Types.NULL);
                else
                    ps.setFloat(1, gradeDto.getQuiz());
                if (gradeDto.getClassPerformance() == -1) ps.setNull(2, Types.NULL);
                else ps.setFloat(2, gradeDto.getClassPerformance());
                if (gradeDto.getPartA() == -1) ps.setNull(3, Types.NULL);
                else ps.setFloat(3, gradeDto.getPartA());
                if (gradeDto.getPartB() == -1) ps.setNull(4, Types.NULL);
                else ps.setFloat(4, gradeDto.getPartB());
                if (gradeDto.getTotal() == -1) ps.setNull(5, Types.NULL);
                else ps.setFloat(5, gradeDto.getTotal());

                ps.setString(6, gradeDto.getGradeLetter());
                ps.setFloat(7, 4);
                ps.setInt(8, gradeDto.getStatus());

                ps.setInt(9, pSemesterId);
                ps.setString(10, pCourseId);
                ps.setInt(11, pExamType);
                ps.setString(12, gradeDto.getStudentId());
            }

            @Override
            public int getBatchSize() {
                return pGradeList.size();
            }

        });
    }

    class MarksSubmissionStatusRowMapper implements RowMapper<MarksSubmissionStatusDto> {
        @Override
        public MarksSubmissionStatusDto mapRow(ResultSet resultSet, int i) throws SQLException {
            MarksSubmissionStatusDto statusDto = new MarksSubmissionStatusDto();

            statusDto.setTotal_part(resultSet.getInt("TOTAL_PART") == 0 ? 2 : resultSet.getInt("TOTAL_PART"));
            statusDto.setPart_a_total(resultSet.getInt("PART_A_TOTAL"));
            statusDto.setPart_b_total(resultSet.getInt("PART_B_TOTAL"));
            statusDto.setStatusId(resultSet.getInt("STATUS"));

            AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
            return atomicReference.get();
        }

    }
        class MarksSubmissionStatusTableRowMapper implements RowMapper<MarksSubmissionStatusDto> {
            @Override
            public MarksSubmissionStatusDto mapRow(ResultSet resultSet, int i) throws SQLException {
                MarksSubmissionStatusDto statusDto = new MarksSubmissionStatusDto();

                statusDto.setCourseId(resultSet.getString("COURSE_ID"));
                statusDto.setCourseNo(resultSet.getString("COURSE_NO"));
                statusDto.setCourseTitle(resultSet.getString("COURSE_TITLE"));
                statusDto.setYear(resultSet.getInt("YEAR"));
                statusDto.setSemester(resultSet.getInt("SEMESTER"));

                statusDto.setPreparerName(resultSet.getString("PREPARER_NAME"));
                statusDto.setScrutinizerName(resultSet.getString("SCRUTINIZER_NAME"));

                statusDto.setOfferedTo(resultSet.getString("PROGRAM_SHORT_NAME").replaceAll("BSC in ",""));
                statusDto.setStatusId(resultSet.getInt("STATUS"));
                statusDto.setStatusName(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")].getLabel());

                String courseTeachers=resultSet.getString("Course_Teachers");
                String courseTeacherArr[]=courseTeachers.split("#");
                ArrayList<CourseTeacherDto> teacherList=new ArrayList();
                for(int t=0;t<courseTeacherArr.length;t++){
                    CourseTeacherDto teacher=new CourseTeacherDto();
                    teacher.setTeacher_name(courseTeacherArr[t]);
                    teacherList.add(teacher);
                }
                statusDto.setCourseTeacherList(teacherList);

                AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
                return atomicReference.get();
            }
        }

}
