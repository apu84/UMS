package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.UGRegistrationResultBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.StudentManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 7/13/2016.
 */

@Component
public class UGRegistrationResultResourceHelper extends
    ResourceHelper<UGRegistrationResult, MutableUGRegistrationResult, Long> {

  @Autowired
  UGRegistrationResultManager mManager;
  @Autowired
  ApplicationCCIManager applicationCCIManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  UGRegistrationResultBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    return null;
  }

  public JsonObject getResultForCarryClearanceAndImprovement(final Request pRequest, final UriInfo pUriInfo) {

    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(mStudentId);
   // Integer x1=student.getCurrentYear();
   // Integer x2=student.getCurrentAcademicSemester();

    List<String> cciTakenCourses = applicationCCIManager.getByStudentIdAndSemester(student.getId(),student.getCurrentEnrolledSemester().getId())
            .stream()
            .map(a->a.getCourse().getId())
            .collect(Collectors.toList());
    //--------------------
      List<UGRegistrationResult> results =
              mManager.getCarryClearanceImprovementCoursesByStudent(student.getCurrentEnrolledSemester().getId(),
                      student.getId()).
                      stream().
                      filter(p-> {
                        if(p.getType().equals(CourseRegType.CARRY)){
                          if(student.getDepartmentId()=="01"){
                            if(student.getCurrentYear()==5 && student.getCurrentAcademicSemester()==2){
                              return  cciTakenCourses.contains(p.getCourse().getId())== false ;
                            }else{
                              return  cciTakenCourses.contains(p.getCourse().getId())== false && p.getCourse().getSemester()
                                      != student.getCurrentAcademicSemester();
                            }
                          }else if(student.getDepartmentId()=="06"){//for textile department 4.1 && 4.2 can give all carryover exam's
                            if(student.getCurrentYear()==4 && (student.getCurrentAcademicSemester()==1 || student.getCurrentAcademicSemester()==2)){
                              return  cciTakenCourses.contains(p.getCourse().getId())== false ;
                            }else{
                              return  cciTakenCourses.contains(p.getCourse().getId())== false && p.getCourse().getSemester()
                                      != student.getCurrentAcademicSemester();
                            }
                          }else{
                            if(student.getCurrentYear()==4 && student.getCurrentAcademicSemester()==2){

                              return  cciTakenCourses.contains(p.getCourse().getId())==false;

                            }else{

                              return  cciTakenCourses.contains(p.getCourse().getId())==false && p.getCourse().getSemester()
                                      != student.getCurrentAcademicSemester();
                            }
                          }
                        }else{
                          return  cciTakenCourses.contains(p.getCourse().getId())==false;
                        }

                      })
              .collect(Collectors.toList());
      //------------
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(UGRegistrationResult result : results) {
      children.add(toJson(result, pUriInfo, localCache));

    }

    object.add("entries", children);
    localCache.invalidate();

    return object.build();

  }

  public JsonObject getResultForApplicationCCIOfCarryClearanceAndImprovement(List<UGRegistrationResult> results,
      final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(UGRegistrationResult result : results) {
      children.add(toJson(result, pUriInfo, localCache));

    }

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @Override
  protected UGRegistrationResultManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<UGRegistrationResult, MutableUGRegistrationResult> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(UGRegistrationResult pReadonly) {
    return "1";
  }
}
