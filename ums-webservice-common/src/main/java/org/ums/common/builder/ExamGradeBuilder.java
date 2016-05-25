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
    }

    public List<StudentGradeDto> build(JsonObject pJsonObject) throws Exception {

        JsonArray entries=pJsonObject.getJsonArray("gradeList");
        JsonObject courseInfo=pJsonObject.getJsonObject("courseInfo");

        List<StudentGradeDto> gradeList=new ArrayList<>(entries.size());
        for (int i = 0; i < entries.size(); i++) {
            JsonObject jsonObject = entries.getJsonObject(i);
            StudentGradeDto grade = new StudentGradeDto();
            grade.setStudentId(jsonObject.getString("studentId"));
            grade.setQuiz((jsonObject.getString("quiz") == null || jsonObject.getString("quiz").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("quiz")));
            grade.setClassPerformance((jsonObject.getString("classPerformance") == null || jsonObject.getString("classPerformance").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("classPerformance")));
            grade.setPartA((jsonObject.getString("partA") == null || jsonObject.getString("partA").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("partA")));
            if(courseInfo.getInt("total_part")==2)
                grade.setPartB((jsonObject.getString("partB")==null  || jsonObject.getString("partB").equalsIgnoreCase(""))? -1:Float.parseFloat(jsonObject.getString("partB")));
            grade.setTotal((jsonObject.getString("total") == null || jsonObject.getString("total").equalsIgnoreCase("")) ? -1 : Float.parseFloat(jsonObject.getString("total")));
            grade.setGradeLetter((jsonObject.getString("gradeLetter")==null  || jsonObject.getString("gradeLetter").equalsIgnoreCase("") )? "" :jsonObject.getString("gradeLetter"));
            grade.setStatus(jsonObject.getInt("status"));

            gradeList.add(grade);
        }
        return gradeList;
    }
}
