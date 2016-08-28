package org.ums.common.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.StudentRecordBuilder;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 08-Aug-16.
 */

@Component
public class StudentRecordResourceHelper extends ResourceHelper<StudentRecord,MutableStudentRecord,Integer> {

  @Autowired
  private StudentRecordManager mManager;

  @Autowired
  private StudentRecordBuilder mBuilder;

  @Autowired
  private StudentManager mStudentManager;

  @Autowired
  private SemesterManager mSemesterManager;





  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  /**
   * This method will return the information whether a student is registered in the current semester or not.
   * @param pStudentId
   * @param pSemesterId
   * @param pYear
   * @param pSemester
   * @param pRequest
   * @param pUriInfo
   * @return
   * @throws Exception
   */

  public JsonObject getStudentRecord(String pStudentId, Integer pSemesterId, Integer pYear, Integer pSemester, final Request pRequest, final UriInfo pUriInfo) throws Exception{
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    Semester studentsSemester = mSemesterManager.get(student.getCurrentEnrolledSemesterId());
    Semester activeSemester = mSemesterManager.getActiveSemester(studentsSemester.getProgramTypeId()) ;

    List<StudentRecord> studentRecords;
    int stId = studentsSemester.getId();
    int aId = activeSemester.getId();

    if(stId==aId){
      studentRecords = getContentManager().getStudentRecords(studentId,studentsSemester.getId(),pYear,pSemester);
    }else{
      studentRecords=new ArrayList<>();
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(StudentRecord studentRecord: studentRecords){
      children.add(toJson(studentRecord,pUriInfo,localCache));
    }

    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected StudentRecordManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<StudentRecord, MutableStudentRecord> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(StudentRecord pReadonly) {
    return pReadonly.getLastModified();
  }
}
