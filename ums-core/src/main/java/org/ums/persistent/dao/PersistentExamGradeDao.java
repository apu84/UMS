package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ikh on 4/29/2016.
 */
public class PersistentExamGradeDao  extends ExamGradeDaoDecorator {

    static String SELECT_THEORY_MARKS = "Select * From UG_THEORY_MARKS  Where Semester_Id='11012016' and Course_Id='CID1' and Exam_Type=1 ";
    static String SELECT_PART_INFO="Select * From MARKS_SUBMISSION_STATUS Where Semester_Id='11012016' and Course_Id='CID1' and Exam_Type=1 ";

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


    class StudentMarksRowMapper implements RowMapper<StudentGradeDto> {
        @Override
        public StudentGradeDto mapRow(ResultSet resultSet, int i) throws SQLException {
            StudentGradeDto marks = new StudentGradeDto();
            marks.setStudentId(resultSet.getString("STUDENT_ID"));
            //marks.setStudentName(resultSet.getString("STUDENT_NAME"));
            marks.setStudentName("Md. Abul Kalam Azad");
            marks.setQuiz(resultSet.getFloat("QUIZ"));
            marks.setClassPerformance(resultSet.getFloat("CLASS_PERFORMANCE"));
            marks.setPartA(resultSet.getFloat("PART_A"));
            marks.setPartB(resultSet.getFloat("PART_B"));
            marks.setPartTotal(resultSet.getFloat("PART_TOTAL"));
            marks.setTotal(resultSet.getFloat("TOTAL"));
            marks.setGradePoint(resultSet.getFloat("GP"));
            marks.setGradeLetter(resultSet.getString("GL"));
            //marks.setStatus(resultSet.getFloat("STATUS"));

            AtomicReference<StudentGradeDto> atomicReference = new AtomicReference<>(marks);
            return atomicReference.get();
        }
    }

    class MarksSubmissionStatusRowMapper implements RowMapper<MarksSubmissionStatusDto> {
        @Override
        public MarksSubmissionStatusDto mapRow(ResultSet resultSet, int i) throws SQLException {
            MarksSubmissionStatusDto statusDto = new MarksSubmissionStatusDto();

            statusDto.setTotal_part(resultSet.getInt("TOTAL_PART")==0?2:resultSet.getInt("TOTAL_PART"));
            statusDto.setPart_a_total(resultSet.getInt("PART_A_TOTAL"));
            statusDto.setPart_b_total(resultSet.getInt("PART_B_TOTAL"));
            statusDto.setStatusId(resultSet.getInt("STATUS"));

            AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
            return atomicReference.get();
        }
    }

}
