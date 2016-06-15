package org.ums.persistent.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.CourseTeacherDto;
import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.Course;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;

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

    static String SELECT_THEORY_MARKS = "Select UG_THEORY_MARKS.*,STUDENT_NAME From UG_THEORY_MARKS,TEST_STUDENT  Where UG_THEORY_MARKS.STUDENT_ID=TEST_STUDENT.STUDENT_ID AND Semester_Id=? and Course_Id=? and Exam_Type=? ";
    static String SELECT_SESSIONAL_MARKS = "Select UG_SESSIONAL_MARKS.*,STUDENT_NAME From UG_SESSIONAL_MARKS,TEST_STUDENT  Where UG_SESSIONAL_MARKS.STUDENT_ID=TEST_STUDENT.STUDENT_ID AND Semester_Id=? and Course_Id=? and Exam_Type=? ";


    static String SELECT_PART_INFO="Select MARKS_SUBMISSION_STATUS.*,COURSE_TYPE From MARKS_SUBMISSION_STATUS,MST_COURSE Where MST_COURSE.Course_Id=MARKS_SUBMISSION_STATUS.Course_Id And " +
            "  MARKS_SUBMISSION_STATUS.Semester_Id=? and MARKS_SUBMISSION_STATUS.Course_Id=? and Exam_Type=? ";

    static String UPDATE_PART_INFO="Update MARKS_SUBMISSION_STATUS Set TOTAL_PART=?,PART_A_TOTAL=?,PART_B_TOTAL=? Where SEMESTER_ID=? and COURSE_ID=? and EXAM_TYPE=? and Status=0";
    static String UPDATE_MARKS_SUBMISSION_STATUS="Update MARKS_SUBMISSION_STATUS Set STATUS=? Where SEMESTER_ID=? and COURSE_ID=? and EXAM_TYPE=? ";

    static String UPDATE_THEORY_MARKS="Update  UG_THEORY_MARKS Set Quiz=?,Class_Performance=?,Part_A=?,Part_B=?,Total=?,Grade_Letter=?,Grade_Point=?,Status=? " +
            " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=?";
    static String UPDATE_SESSIONAL_MARKS="Update  UG_SESSIONAL_MARKS Set Total=?,Grade_Letter=?,Grade_Point=?,Status=? " +
            " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=?";

    static String SELECT_GRADE_SUBMISSION_TABLE_TEACHER="Select tmp5.*,Status From ( " +
            "Select tmp4.*,MVIEW_TEACHERS.TEACHER_NAME Scrutinizer_Name,getCourseTeacher(semester_id,course_id) Course_Teachers From " +
            "( " +
            "Select tmp3.*,MVIEW_TEACHERS.TEACHER_NAME Preparer_name From  " +
            "( " +
            "Select tmp2.*,Mst_Course.Course_Title,Program_Short_Name,MST_COURSE.COURSE_NO,Year,Semester from ( " +
            "Select semester_id,course_id,preparer preparer_id,scrutinizer scrutinizer_id From PREPARER_SCRUTINIZER Where Semester_Id=? And (Preparer=? or Scrutinizer=?) " +
            "Union " +
            "Select tmp1.semester_id,tmp1.course_id,preparer preparer_id,scrutinizer scrutinizer_id from ( " +
            "Select Semester_Id,Course_Id From COURSE_TEACHER Where Semester_Id=? and Teacher_Id=?)tmp1,PREPARER_SCRUTINIZER " +
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

    static String SELECT_GRADE_SUBMISSION_TABLE_HEAD="Select Ms_Status.Semester_Id,Exam_Type,Mst_Course.Course_Id,Course_No,Course_Title ,CrHr,Course_Type,Course_Category,Offer_By,Year,Semester,Mst_Course.Syllabus_Id, " +
        "getCourseTeacher(Ms_Status.semester_id,Mst_Course.course_id) Course_Teachers, " +
        "MST_PROGRAM.PROGRAM_SHORT_NAME,getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'P') PREPARER_NAME, " +
        "getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'S') SCRUTINIZER_NAME,STATUS  " +
        "From MARKS_SUBMISSION_STATUS Ms_Status,Mst_Course,MST_SYLLABUS,MST_PROGRAM " +
        "Where Ms_Status.Semester_Id=? And Exam_Type=? " +
        "And Ms_Status.Course_Id=Mst_Course.Course_Id " +
        "And MST_SYLLABUS.SYLLABUS_ID=MST_COURSE.SYLLABUS_ID " +
        "And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID " +
        "And Offer_By in " +
        "(Select dept_id From MVIEW_Teachers  where Teacher_Id =?) ";

    static String SELECT_GRADE_SUBMISSION_TABLE_CoE="Select Ms_Status.Semester_Id,Exam_Type,Mst_Course.Course_Id,Course_No,Course_Title ,CrHr,Course_Type,Course_Category,Offer_By,Year,Semester,Mst_Course.Syllabus_Id, " +
        "getCourseTeacher(Ms_Status.semester_id,Mst_Course.course_id) Course_Teachers, " +
        "MST_PROGRAM.PROGRAM_SHORT_NAME,getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'P') PREPARER_NAME, " +
        "getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'S') SCRUTINIZER_NAME, STATUS  " +
        "From MARKS_SUBMISSION_STATUS Ms_Status,Mst_Course,MST_SYLLABUS,MST_PROGRAM " +
        "Where Ms_Status.Semester_Id=? And Exam_Type=? " +
        "And Ms_Status.Course_Id=Mst_Course.Course_Id " +
        "And MST_SYLLABUS.SYLLABUS_ID=MST_COURSE.SYLLABUS_ID " +
        "And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID " +
        "And Offer_By =? " ;

    static String UPDATE_STATUS_SAVE_RECHECK="Update TABLE_NAME Set RECHECK_STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and " +
        " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
    static String UPDATE_STATUS_SAVE_APPROVE="Update TABLE_NAME Set RECHECK_STATUS=?,STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and " +
        " Status in  (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

    static String UPDATE_STATUS_RECHECK_RECHECK="Update TABLE_NAME Set RECHECK_STATUS=?,STATUS=?   Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and " +
        " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
    static String UPDATE_STATUS_RECHECK_APPROVE="Update TABLE_NAME Set RECHECK_STATUS=?,STATUS=?   Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and  " +
        " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

    static String UPDATE_STATUS_APPROVE="Update TABLE_NAME Set RECHECK_STATUS=?, STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and  " +
        " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

    static String CHECK_TEACHER_ROLE="Select * From  " +
        "( " +
        "Select 'preparer' Role,1 Serial From PREPARER_SCRUTINIZER Where Preparer=? And Semester_Id=? and Course_Id=?  " +
        "Union " +
        "Select 'scrutinizer' Role,2 Serial From PREPARER_SCRUTINIZER Where Scrutinizer=? And Semester_Id=? and Course_Id=? " +
        "Union " +
        "Select 'courseteacher' Role,3 Role From Course_Teacher Where Teacher_Id =? and Semester_Id=? and Course_Id=? " +
        ")tmp1 Order by Serial" ;

    static String CHECK_HEAD_ROLE="Select 'head' Role, 1 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=? and Role_Id=22" ;

    static String CHECK_COE_ROLE="Select 'coe' Role, 1 Serial From USERS Where User_Id=? and Role_Id=71 " +
        "Union " +
        "Select 'CoE' Role, 2 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=?  And Role_Id=71";

    static String CHECK_VC_ROLE="Select 'vc' Role, 1 Serial From USERS Where User_Id=? and Role_Id=91 " +
        "Union " +
        "Select 'vc' Role, 2 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=?  And Role_Id=91";

    String CHART_DATA="Select Grade_Letter,sum(Total) Total, max(Color) Color From   " +
        "(  " +
        "select Grade_Letter,Count(Total) Total,'' Color From TABLE_NAME Where Semester_Id=? And Course_Id=? and Exam_Type=? Group by Grade_Letter  " +
        "Union  " +
        "Select 'A+' Grade_Letter, 0 Total, '#FF0F00' Color From Dual  " +
        "Union  " +
        "Select 'A' Grade_Letter, 0 Total, '#FF6600' Color From Dual  " +
        "Union  " +
        "Select 'A-' Grade_Letter, 0 Total, '#FF9E01' Color From Dual  " +
        "Union  " +
        "Select 'B+' Grade_Letter, 0 Total, '#FCD202' Color From Dual  " +
        "Union  " +
        "Select 'B' Grade_Letter, 0 Total, '#F8FF01' Color From Dual  " +
        "Union  " +
        "Select 'B-' Grade_Letter, 0 Total, '#B0DE09' Color From Dual  " +
        "Union  " +
        "Select 'C+' Grade_Letter, 0 Total, '#04D215' Color From Dual  " +
        "Union  " +
        "Select 'C' Grade_Letter, 0 Total, '#0D8ECF' Color From Dual  " +
        "Union  " +
        "Select 'D' Grade_Letter, 0 Total, '#0D52D1' Color From Dual  " +
        "Union  " +
        "Select 'F' Grade_Letter, 0 Total, '#2A0CD0' Color From Dual  " +
        ")Tmp Group by Grade_Letter Order by Decode(Grade_Letter,'A+',1,'A',2,'A-',3,'B+',4,'B',5,'B-',6,'C+',7,'C',8,'D',9,'F',10)  " ;

    private JdbcTemplate mJdbcTemplate;

    public PersistentExamGradeDao(final JdbcTemplate pJdbcTemplate) {
        mJdbcTemplate = pJdbcTemplate;
    }

    @Override
    public List<StudentGradeDto> getAllGrades(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception {

        String query="";
        if(courseType==CourseType.THEORY)
            query=SELECT_THEORY_MARKS + " Order by UG_THEORY_MARKS.Student_Id,Status  ";
        else if(courseType==CourseType.SESSIONAL)
            query=SELECT_SESSIONAL_MARKS + " Order by UG_SESSIONAL_MARKS.Student_Id,Status  ";

        return mJdbcTemplate.query(query,new Object[]{pSemesterId,pCourseId,pExamType}, new StudentMarksRowMapper(courseType));
    }

    @Override
    public List<GradeChartDataDto> getChartData(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception {
        String query =getQuery(CHART_DATA,courseType);
        return mJdbcTemplate.query(query,new Object[]{pSemesterId,pCourseId,pExamType}, new ChartDataRowMapper());
    }

    private String getQuery(String orgQuery,CourseType courseType){
        String query="";
        if(courseType==CourseType.THEORY)
            query=orgQuery.replaceAll("TABLE_NAME", " UG_THEORY_MARKS");
        else if(courseType==CourseType.SESSIONAL)
            query=orgQuery.replaceAll("TABLE_NAME", "UG_SESSIONAL_MARKS");
        return query;
    }

    @Override
    public MarksSubmissionStatusDto getMarksSubmissionStatus(int pSemesterId,String pCourseId,int pExamType) throws Exception {
        String query = SELECT_PART_INFO;
        return mJdbcTemplate.queryForObject(query,new Object[]{pSemesterId,pCourseId,pExamType}, new MarksSubmissionStatusRowMapper());
    }

    @Override
    public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(int pSemesterId,int pExamType,String teacherId,String deptId,String userRole) throws Exception {
        String query="";
        if(userRole.equals("T")){  //Teacher
            query = SELECT_GRADE_SUBMISSION_TABLE_TEACHER;
            return mJdbcTemplate.query(query, new Object[] {pSemesterId,teacherId,teacherId,pSemesterId,teacherId},new MarksSubmissionStatusTableRowMapper());
        }
        else if(userRole.equals("H")){  //Head
            query = SELECT_GRADE_SUBMISSION_TABLE_HEAD;
            return mJdbcTemplate.query(query, new Object[]{pSemesterId,pExamType,teacherId},new MarksSubmissionStatusTableRowMapper());
        }
        else if(userRole.equals("C")){  //CoE
            query = SELECT_GRADE_SUBMISSION_TABLE_CoE;
            return mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamType,deptId} ,new MarksSubmissionStatusTableRowMapper());
        }
        else if(userRole.equals("V")){  //CoE
            query = SELECT_GRADE_SUBMISSION_TABLE_CoE;
            return mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamType,deptId} ,new MarksSubmissionStatusTableRowMapper());
        }
        return null;
    }

    @Override
    public List<String> getRoleForTeacher(String pTeacherId,int  pSemesterId,String pCourseId) throws Exception {
        String query = CHECK_TEACHER_ROLE;
        return mJdbcTemplate.query(query, new Object[]{pTeacherId, pSemesterId, pCourseId,pTeacherId, pSemesterId, pCourseId,pTeacherId, pSemesterId, pCourseId}, new RoleRowMapper());
    }
    @Override
    public List<String> getRoleForHead(String pUserId) throws Exception {
        String query = CHECK_HEAD_ROLE;
        return mJdbcTemplate.query(query, new Object[]{pUserId},new RoleRowMapper());
    }
    @Override
    public List<String> getRoleForCoE(String pUserId) throws Exception {
        String query = CHECK_COE_ROLE;
        return mJdbcTemplate.query(query, new Object[]{pUserId,pUserId},new RoleRowMapper());
    }
    @Override
    public List<String> getRoleForVC(String pUserId) throws Exception {
        String query = CHECK_VC_ROLE;
        return mJdbcTemplate.query(query, new Object[]{pUserId,pUserId},new RoleRowMapper());
    }

    @Override
    public int updatePartInfo(int pSemesterId,String pCourseId,int pExamType,int pTotalPart,int partA,int partB) throws Exception {
        String query = UPDATE_PART_INFO;
        return mJdbcTemplate.update(query, pTotalPart,partA,partB,pSemesterId,pCourseId,pExamType);

    }
    @Override
    public int updateCourseMarksSubmissionStatus(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,CourseMarksSubmissionStatus status) throws Exception {
        String query = UPDATE_MARKS_SUBMISSION_STATUS;
        return mJdbcTemplate.update(query,status.getId(), pSemesterId,pCourseId,pExamType);
    }

    class ChartDataRowMapper implements RowMapper<GradeChartDataDto> {
        @Override
        public GradeChartDataDto mapRow(ResultSet resultSet, int i) throws SQLException {
            GradeChartDataDto data = new GradeChartDataDto();
            data.setGradeLetter(resultSet.getString("GRADE_LETTER"));
            data.setTotal(resultSet.getInt("TOTAL"));
            data.setColor(resultSet.getString("COLOR"));
            AtomicReference<GradeChartDataDto> atomicReference = new AtomicReference<>(data);
            return atomicReference.get();
        }
    }

    class StudentMarksRowMapper implements RowMapper<StudentGradeDto> {
        CourseType courseType;
        public StudentMarksRowMapper(CourseType courseType){
            this.courseType=courseType;
        }
        @Override
        public StudentGradeDto mapRow(ResultSet resultSet, int i) throws SQLException {
            StudentGradeDto marks = new StudentGradeDto();
            marks.setStudentId(resultSet.getString("STUDENT_ID"));
            //marks.setStudentName(resultSet.getString("STUDENT_NAME"));
            marks.setStudentName(resultSet.getString("STUDENT_NAME"));

            if(courseType==CourseType.THEORY) {
                float quiz = resultSet.getFloat("QUIZ");
                marks.setQuiz(resultSet.wasNull() ? null : quiz);

                float classPerformance = resultSet.getFloat("CLASS_PERFORMANCE");
                marks.setClassPerformance(resultSet.wasNull() ? null : classPerformance);

                float partA = resultSet.getFloat("PART_A");
                marks.setPartA(resultSet.wasNull() ? null : partA);

                float partB = resultSet.getFloat("PART_B");
                marks.setPartB(resultSet.wasNull() ? null : partB);

                marks.setPartTotal(resultSet.getFloat("PART_TOTAL"));
            }
            float total = resultSet.getFloat("TOTAL");
            marks.setTotal(resultSet.wasNull() ? null : total);

            marks.setGradePoint(resultSet.getFloat("GRADE_POINT"));
            marks.setGradeLetter(resultSet.getString("GRADE_LETTER"));

            marks.setStatus(StudentMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
            marks.setStatusId(resultSet.getInt("STATUS"));
            marks.setRecheckStatusId(resultSet.getInt("RECHECK_STATUS"));
            marks.setRecheckStatus(RecheckStatus.values()[resultSet.getInt("RECHECK_STATUS")]);

            AtomicReference<StudentGradeDto> atomicReference = new AtomicReference<>(marks);
            return atomicReference.get();
        }
    }

    public boolean saveGradeSheet(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> pGradeList) throws Exception {
        batchUpdateGrade(pSemesterId, pCourseId, pExamType,courseType, pGradeList);
        return true;
    }

    public void batchUpdateGrade(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> pGradeList){
        String sql ="";
        if(courseType==CourseType.THEORY)
            sql=UPDATE_THEORY_MARKS;
        else if(courseType==CourseType.SESSIONAL)
            sql=UPDATE_SESSIONAL_MARKS;

        mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = pGradeList.get(i);
                if(courseType==CourseType.THEORY) {
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
                    ps.setInt(8, gradeDto.getStatusId());

                    ps.setInt(9, pSemesterId);
                    ps.setString(10, pCourseId);
                    ps.setInt(11, pExamType);
                    ps.setString(12, gradeDto.getStudentId());
                }
                if(courseType==CourseType.SESSIONAL) {
                    if (gradeDto.getTotal() == -1) ps.setNull(1, Types.NULL);
                    else ps.setFloat(1, gradeDto.getTotal());

                    ps.setString(2, gradeDto.getGradeLetter());
                    ps.setFloat(3, 4);
                    ps.setInt(4, gradeDto.getStatusId());

                    ps.setInt(5, pSemesterId);
                    ps.setString(6, pCourseId);
                    ps.setInt(7, pExamType);
                    ps.setString(8, gradeDto.getStudentId());
                }
            }
            @Override
            public int getBatchSize() {
                return pGradeList.size();
            }

        });
    }

    @Override
    public boolean updateGradeStatus_Save(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        batchUpdateGradeStatus_Save(pSemesterId, pCourseId, pExamType,courseType,recheckList,approveList);
        return true;
    }

    public void batchUpdateGradeStatus_Save(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList){

        String query =getQuery(UPDATE_STATUS_SAVE_RECHECK,courseType);
        mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = recheckList.get(i);
                ps.setInt(1, gradeDto.getRecheckStatus().getId());
                ps.setInt(2, pSemesterId);
                ps.setString(3, pCourseId);
                ps.setInt(4, pExamType);
                ps.setString(5, gradeDto.getStudentId());
                ps.setString(6, gradeDto.getPreviousStatusString());
                ps.setString(7, gradeDto.getPreviousStatusString());
            }
            @Override
            public int getBatchSize() {
                return recheckList.size();
            }

        });

        query =getQuery(UPDATE_STATUS_SAVE_APPROVE,courseType);
        mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = approveList.get(i);
                ps.setInt(1, gradeDto.getRecheckStatus().getId());
                ps.setInt(2, gradeDto.getStatus().getId());
                ps.setInt(3, pSemesterId);
                ps.setString(4, pCourseId);
                ps.setInt(5, pExamType);
                ps.setString(6, gradeDto.getStudentId());
                ps.setString(7, gradeDto.getPreviousStatusString());
                ps.setString(8, gradeDto.getPreviousStatusString());
            }
            @Override
            public int getBatchSize() {
                return approveList.size();
            }

        });
    }

    @Override
    public boolean updateGradeStatus_Recheck(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        batchUpdateGradeStatus_Recheck(pSemesterId, pCourseId, pExamType,courseType, recheckList, approveList);
        return true;
    }

    @Override
    public int rejectRecheckRequest(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception {
        String query = "Update  UG_THEORY_MARKS Set RECHECK_STATUS="+RecheckStatus.RECHECK_FALSE.getId()+"  Where Semester_Id=? And Course_Id=? and Exam_Type=? ";
        return mJdbcTemplate.update(query,pSemesterId,pCourseId,pExamType);

    }
    @Override
    public int approveRecheckRequest(int pSemesterId,String pCourseId,int pExamType,CourseType courseType) throws Exception {
        String query = "Update  UG_THEORY_MARKS Set STATUS="+StudentMarksSubmissionStatus.NONE.getId()+"  Where Semester_Id=? And Course_Id=? and Exam_Type=? and Status=  " +StudentMarksSubmissionStatus.ACCEPTED.getId()+
            " and RECHECK_STATUS="+RecheckStatus.RECHECK_TRUE .getId();
        return mJdbcTemplate.update(query,pSemesterId,pCourseId,pExamType);
    }
    public void batchUpdateGradeStatus_Recheck(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList){

        String query =getQuery(UPDATE_STATUS_RECHECK_RECHECK,courseType);
        mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = recheckList.get(i);
                ps.setInt(1, gradeDto.getRecheckStatus().getId());
                ps.setInt(2, gradeDto.getStatus().getId());
                ps.setInt(3, pSemesterId);
                ps.setString(4, pCourseId);
                ps.setInt(5, pExamType);
                ps.setString(6, gradeDto.getStudentId());
                ps.setString(7, gradeDto.getPreviousStatusString());
                ps.setString(8, gradeDto.getPreviousStatusString());
            }
            @Override
            public int getBatchSize() {
                return recheckList.size();
            }

        });

        query =getQuery(UPDATE_STATUS_RECHECK_APPROVE,courseType);
        mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = approveList.get(i);
                ps.setInt(1, gradeDto.getRecheckStatus().getId());
                ps.setInt(2, gradeDto.getStatus().getId());
                ps.setInt(3, pSemesterId);
                ps.setString(4, pCourseId);
                ps.setInt(5, pExamType);
                ps.setString(6, gradeDto.getStudentId());
                ps.setString(7, gradeDto.getPreviousStatusString());
                ps.setString(8, gradeDto.getPreviousStatusString());
            }
            @Override
            public int getBatchSize() {
                return approveList.size();
            }

        });
    }

    @Override
    public boolean updateGradeStatus_Approve(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList) throws Exception {
        batchUpdateGradeStatus_Approve(pSemesterId, pCourseId, pExamType,courseType, recheckList, approveList);
        return true;
    }
    public void batchUpdateGradeStatus_Approve(int pSemesterId,String pCourseId,int pExamType,CourseType courseType,List<StudentGradeDto> recheckList,List<StudentGradeDto> approveList){
        String query =getQuery(UPDATE_STATUS_APPROVE,courseType);
        mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentGradeDto gradeDto = approveList.get(i);
                ps.setInt(1, RecheckStatus.RECHECK_FALSE.getId());
                ps.setInt(2, gradeDto.getStatus().getId());
                ps.setInt(3, pSemesterId);
                ps.setString(4, pCourseId);
                ps.setInt(5, pExamType);
                ps.setString(6, gradeDto.getStudentId());
                ps.setString(7, gradeDto.getPreviousStatusString());
                ps.setString(8, gradeDto.getPreviousStatusString());
            }
            @Override
            public int getBatchSize() {
                return approveList.size();
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
            statusDto.setStatus(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
            statusDto.setCourseType(CourseType.get(resultSet.getInt("COURSE_TYPE")));
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
                ArrayList<CourseTeacherDto> teacherList=new ArrayList();
                if(courseTeachers!=null && !courseTeachers.equalsIgnoreCase("")) {
                    String courseTeacherArr[] = courseTeachers.split("#");

                    for (int t = 0; t < courseTeacherArr.length; t++) {
                        CourseTeacherDto teacher = new CourseTeacherDto();
                        teacher.setTeacher_name(courseTeacherArr[t]);
                        teacherList.add(teacher);
                    }
                }
                statusDto.setCourseTeacherList(teacherList);

                AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
                return atomicReference.get();
            }
        }
    class RoleRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            String a=resultSet.getString("ROLE");

            AtomicReference<String> atomicReference = new AtomicReference<>(a);
            return atomicReference.get();
        }
    }




}
