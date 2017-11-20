package org.ums.academic.resource.fee;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.StudentStatus;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.manager.StudentManager;
import org.ums.resource.Resource;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/fee-category")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FeeCategoryResource extends Resource {
  @Autowired
  FeeCategoryManager mFeeCategoryManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  UserManager mUserManager;

  @GET
  @Path("/all")
  public List<FeeCategory> getFeeCategories() throws Exception {
    List<FeeCategory> feeCategories = mFeeCategoryManager.getAll();
    User user = getLoggedUser();
    Student student = mStudentManager.get(user.getId());
    if (student != null) {
      return getFeeCategoriesBasedOnGraduationType(feeCategories, student);
    } else {
      return feeCategories;
    }
  }

  @GET
  @Path("/type/{typeId}")
  public List<FeeCategory> getFeeCategories(@PathParam("typeId") Integer pTypeId) throws Exception {
    return mFeeCategoryManager.getFeeCategories(pTypeId);
  }

  private User getLoggedUser() {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    return user;
  }

  private List<FeeCategory> getFeeCategoriesBasedOnGraduationType(List<FeeCategory> pFeeCategories, Student pStudent) {
    if (pStudent.getStatus().equals(StudentStatus.PASSED)) {
      return pFeeCategories.stream()
          .filter(f -> f.getDependencies() != null && f.getDependencies().charAt(0) == 'G')
          .collect(Collectors.toList());
    } else if (pStudent.getStatus().equals(StudentStatus.ACTIVE)) {
      return pFeeCategories.stream()
          .filter(f -> f.getDependencies() == null ? true : f.getDependencies().charAt(0) != 'G')
          .collect(Collectors.toList());
    } else return null;
  }
}
