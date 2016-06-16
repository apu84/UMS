package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.enums.CourseType;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
@Component
public class ExamGradeBuilder implements Builder<ExamGrade, MutableExamGrade> {

    @Override
    public void build(JsonObjectBuilder pBuilder, ExamGrade pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {

    }

    @Override
    public void build(MutableExamGrade pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    }

    public void build(MarksSubmissionStatusDto partInfoDto,JsonObject pJsonObject) throws Exception{
        JsonObject course=pJsonObject.getJsonObject("courseInfo");
        partInfoDto.setCourseId(course.getString("course_id"));
        partInfoDto.setSemesterId(course.getInt("semester_id"));
        partInfoDto.setExamType(course.getInt("exam_type"));
        partInfoDto.setTotal_part(course.getInt("total_part"));
        partInfoDto.setPart_a_total(course.getInt("part_a_total"));
        partInfoDto.setPart_b_total(course.getInt("part_b_total"));
        partInfoDto.setCourseType(CourseType.get(course.getInt("course_type")));
    }

    public List<StudentGradeDto> build(JsonObject pJsonObject) throws Exception {

        JsonArray entries=pJsonObject.getJsonArray("gradeList");
        JsonObject courseInfo=pJsonObject.getJsonObject("courseInfo");

        List<StudentGradeDto> gradeList=new ArrayList<>(entries.size());
        for (int i = 0; i < entries.size(); i++) {
            JsonObject jsonObject = entries.getJsonObject(i);
            StudentGradeDto grade = new StudentGradeDto();
            grade.setStudentId(jsonObject.getString("studentId"));
            if(courseInfo.getInt("course_type")==1) { // For  only theory courses
                grade.setQuiz((jsonObject.getString("quiz") == null || jsonObject.getString("quiz").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("quiz")));
                grade.setClassPerformance((jsonObject.getString("classPerformance") == null || jsonObject.getString("classPerformance").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("classPerformance")));
                grade.setPartA((jsonObject.getString("partA") == null || jsonObject.getString("partA").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("partA")));
                if (courseInfo.getInt("total_part") == 2)
                    grade.setPartB((jsonObject.getString("partB") == null || jsonObject.getString("partB").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("partB")));
            }
            grade.setTotal((jsonObject.getString("total") == null || jsonObject.getString("total").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("total")));
            grade.setGradeLetter((jsonObject.getString("gradeLetter")==null  || jsonObject.getString("gradeLetter").equalsIgnoreCase("") )? "" :jsonObject.getString("gradeLetter"));
            grade.setStatusId(jsonObject.getInt("statusId"));
            grade.setStatus(StudentMarksSubmissionStatus.values()[jsonObject.getInt("statusId")]);

            //grade.setrec(RecheckStatus.values()[jsonObject.getInt("status")]);
            //grade.setStatus(StudentMarksSubmissionStatus.values()[jsonObject.getInt("status")]);

            gradeList.add(grade);
        }
        return gradeList;
    }

    public ArrayList<List<StudentGradeDto>> buildForRecheckApproveGrade(String action,String actor,JsonObject pJsonObject) throws Exception {

        ArrayList<List<StudentGradeDto>> recheckApproveList=new ArrayList<>();
        JsonArray recheckEntries=pJsonObject.getJsonArray("recheckList");
        JsonArray approveEntries=pJsonObject.getJsonArray("approveList");

        List<StudentGradeDto> recheckList=new ArrayList<>(recheckEntries.size());
        List<StudentGradeDto> approveList=new ArrayList<>(approveEntries.size());

        if(action.equalsIgnoreCase("save")) {
            for (int i = 0; i < recheckEntries.size(); i++) {
                JsonObject jsonObject = recheckEntries.getJsonObject(i);
                StudentGradeDto grade = new StudentGradeDto();
                grade.setRecheckStatus(RecheckStatus.RECHECK_TRUE);
                grade.setStudentId(jsonObject.getString("studentId"));
                grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
                recheckList.add(grade);
            }
            for (int i = 0; i < approveEntries.size(); i++) {
                JsonObject jsonObject = approveEntries.getJsonObject(i);
                StudentGradeDto grade = new StudentGradeDto();
                grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
                grade.setStatus(getMarksSubmissionStatus(actor, action, "approve"));
                grade.setStudentId(jsonObject.getString("studentId"));
                grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
                approveList.add(grade);
            }
        }
        else if(action.equalsIgnoreCase("recheck") || action.equalsIgnoreCase("recheck_request_submit")) {
            for (int i = 0; i < recheckEntries.size(); i++) {
                JsonObject jsonObject = recheckEntries.getJsonObject(i);
                StudentGradeDto grade = new StudentGradeDto();
                grade.setRecheckStatus(RecheckStatus.RECHECK_TRUE);
                grade.setStatus(getMarksSubmissionStatus(actor,action,"recheck"));
                grade.setStudentId(jsonObject.getString("studentId"));
                if(action.equalsIgnoreCase("recheck") )
                    grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
                else if( action.equalsIgnoreCase("recheck_request_submit"))
                    grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor,action));
                recheckList.add(grade);
            }
            for (int i = 0; i < approveEntries.size(); i++) {
                JsonObject jsonObject = approveEntries.getJsonObject(i);
                StudentGradeDto grade = new StudentGradeDto();
                grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
                grade.setStatus(getMarksSubmissionStatus(actor,action,"approve"));
                grade.setStudentId(jsonObject.getString("studentId"));
                grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
                approveList.add(grade);
            }
        }
        else if(action.equalsIgnoreCase("approve")) {
            for (int i = 0; i < approveEntries.size(); i++) {
                JsonObject jsonObject = approveEntries.getJsonObject(i);
                StudentGradeDto grade = new StudentGradeDto();
                grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
                grade.setStatus(getMarksSubmissionStatus(actor,action,"approve"));
                grade.setStudentId(jsonObject.getString("studentId"));
                grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
                approveList.add(grade);
            }
        }
        recheckApproveList.add(recheckList);
        recheckApproveList.add(approveList);
        return recheckApproveList;
    }

    private StudentMarksSubmissionStatus getMarksSubmissionStatus(String actor,String action,String gradeType){
        if(actor.equals("scrutinizer")){
            if(action.equals("save") && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.SCRUTINIZE;
            else if(action.equals("recheck")  && gradeType.equals("recheck"))
                return StudentMarksSubmissionStatus.NONE;
            else if(action.equals("recheck")  && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.SCRUTINIZE;
            else if(action.equals("approve")   && gradeType.equals("recheck"))
                return StudentMarksSubmissionStatus.SCRUTINIZED;
            else if(action.equals("approve") && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.SCRUTINIZED;
        }
        if(actor.equals("head")){
            if(action.equals("save") && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.APPROVE;
            else if(action.equals("recheck")  && gradeType.equals("recheck"))
                return StudentMarksSubmissionStatus.SUBMITTED;
            else if(action.equals("recheck")  && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.APPROVE;
            else if(action.equals("approve") && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.APPROVED;
        }
        if(actor.equals("coe")){
            if(action.equals("save") && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.ACCEPT;
            else if(action.equals("recheck")  && gradeType.equals("recheck"))
                return StudentMarksSubmissionStatus.SCRUTINIZED;
            else if(action.equals("recheck")  && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.ACCEPT;
            else if(action.equals("approve")  && gradeType.equals("approve"))
                return StudentMarksSubmissionStatus.ACCEPTED;
            else if(action.equals("recheck_request_submit"))
                return StudentMarksSubmissionStatus.ACCEPTED;

        }
        return null;
    }

    private String getPrevMarksSubmissionStatus(String actor){
        if(actor.equals("scrutinizer"))
            return StudentMarksSubmissionStatus.SUBMITTED.getId()+","+StudentMarksSubmissionStatus.SCRUTINIZE.getId();
        if(actor.equals("head"))
            return StudentMarksSubmissionStatus.SCRUTINIZED.getId()+","+StudentMarksSubmissionStatus.APPROVE.getId();
        if(actor.equals("coe"))
            return StudentMarksSubmissionStatus.APPROVED.getId()+","+StudentMarksSubmissionStatus.ACCEPT.getId();

        return null;
    }
    private String getPrevMarksSubmissionStatus(String actor,String action){
        if(actor.equals("coe") && action.equalsIgnoreCase("recheck_request_submit"))
            return String.valueOf(StudentMarksSubmissionStatus.ACCEPTED.getId());

        return null;
    }



}
