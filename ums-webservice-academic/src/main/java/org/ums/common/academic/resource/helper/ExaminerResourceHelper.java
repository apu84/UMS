package org.ums.common.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.cache.LocalCache;
import org.ums.common.builder.ExaminerBuilder;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.exceptions.ValidationException;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.UserManager;
import org.ums.message.MessageResource;
import org.ums.persistent.model.PersistentExaminer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class ExaminerResourceHelper
    extends
    AbstractAssignedTeacherResourceHelper<Examiner, MutableExaminer, Integer, AssignedTeacherManager<Examiner, MutableExaminer, Integer>> {
  @Autowired
  @Qualifier("examinerManager")
  AssignedTeacherManager<Examiner, MutableExaminer, Integer> mExaminerManager;

  @Autowired
  private ExaminerBuilder mBuilder;

  @Autowired
  private MessageResource mMessageResource;

  @Autowired
  private UserManager mUserManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @Override
  protected AssignedTeacherManager<Examiner, MutableExaminer, Integer> getContentManager() {
    return mExaminerManager;
  }

  @Override
  protected ExaminerBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(Examiner pReadonly) {
    return pReadonly.getLastModified();
  }

  protected void modifyContent(JsonObject pJsonObject) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      String updateType = jsonObject.getString("updateType");
      Object obj = jsonObject.get("programId");
      Integer programId;
      try {
        programId = jsonObject.getInt("programId");
      } catch(Exception e) {
        programId = Integer.parseInt(jsonObject.getString("programId"));

      }
      /*
       * if(((Integer)jsonObject.getInt("programId")!=null )){ programId =
       * jsonObject.getInt("programId"); }else{
       * 
       * programId = Integer.parseInt(jsonObject.getString("programId")); }
       */
      MutableExaminer mutableExaminer = new PersistentExaminer();
      getBuilder().build(mutableExaminer, jsonObject, localCache);
      User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
      switch(updateType) {
        case "insert":
          // First validate whether there is already an entry for this course
          List<Examiner> examiners =
              mExaminerManager.getAssignedTeachers(programId, mutableExaminer.getSemesterId(),
                  mutableExaminer.getCourseId(), user.getDepartment().getId());
          if(examiners.size() > 0
              && (!StringUtils.isEmpty(examiners.get(0).getPreparerId()) || !StringUtils
                  .isEmpty(examiners.get(0).getScrutinizerId()))) {
            throw new ValidationException(mMessageResource.getMessage("examiner.already.assigned",
                mutableExaminer.getCourse().getNo()));
          }
          mutableExaminer.commit(false);
          break;
        case "update":
          Examiner Examiner = mExaminerManager.get(mutableExaminer.getId());
          MutableExaminer updateExaminer = Examiner.edit();
          updateExaminer.setPreparer(mutableExaminer.getPreparer());
          updateExaminer.setScrutinizer(mutableExaminer.getScrutinizer());
          updateExaminer.setSemester(mutableExaminer.getSemester());
          updateExaminer.setCourse(mutableExaminer.getCourse());
          updateExaminer.commit(true);
          break;
        case "delete":
          Examiner teacher = mExaminerManager.get(mutableExaminer.getId());
          MutableExaminer deleteExaminer = teacher.edit();
          deleteExaminer.delete();
          break;
      }
    }

  }
}
