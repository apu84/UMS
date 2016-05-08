package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableExamRoutine;

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

    public List<StudentGradeDto> build(JsonObject pJsonObject) throws Exception {

        JsonArray entries=pJsonObject.getJsonArray("gradeList");

        List<StudentGradeDto> gradeList=new ArrayList<>(entries.size());
        for (int i = 0; i < entries.size(); i++) {
            JsonObject jsonObject = entries.getJsonObject(i);
            StudentGradeDto grade = new StudentGradeDto();
            grade.setStudentId(jsonObject.getString("studentId"));
            grade.setQuiz((jsonObject.getString("quiz") == null || jsonObject.getString("quiz").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("quiz")));

//            try {
//                if (jsonObject.getString("classPerformance") == null || jsonObject.getString("classPerformance").equalsIgnoreCase("")) {
//                    grade.setClassPerformance(-1);
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }

            grade.setClassPerformance((jsonObject.getString("classPerformance") == null || jsonObject.getString("classPerformance").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("classPerformance")));


            grade.setPartA((jsonObject.getString("partA") == null || jsonObject.getString("partA").equalsIgnoreCase(""))? -1 : Float.parseFloat(jsonObject.getString("partA")));

            if(pJsonObject.getInt("total_part")==2)
                grade.setPartB((jsonObject.getString("partB")==null  || jsonObject.getString("partB").equalsIgnoreCase(""))? -1:Float.parseFloat(jsonObject.getString("partB")));
            //grade.setTotal(jsonObject.getString("partTotal"));
            //grade.setTotal(jsonObject.getString("total"));
            grade.setGradeLetter((jsonObject.getString("gradeLetter")==null  || jsonObject.getString("gradeLetter").equalsIgnoreCase("") )? "" :jsonObject.getString("gradeLetter"));
            //routine.setCourseId(jsonObject.getString("gradeLetter"));
            grade.setStatus(jsonObject.getInt("status"));





            gradeList.add(grade);
        }
        return gradeList;
    }
}
