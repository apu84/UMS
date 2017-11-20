package org.ums.academic.resource.fee;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.StudentStatus;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusLogManager;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.manager.StudentManager;
import org.ums.resource.Resource;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
  @Autowired
  CertificateStatusLogManager mCertificateStatusLogManager;
  @Autowired
  CertificateStatusManager mCertificateStatusManager;

  @GET
  @Path("/all")
  public List<FeeCategory> getFeeCategories() throws Exception {
    List<FeeCategory> feeCategories = mFeeCategoryManager.getAll();
    User user = getLoggedUser();
    Student student = mStudentManager.get(user.getId());
    if(student != null) {
      return getFeeCategoriesBasedOnGraduationType(feeCategories, student);
    }
    else {
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
    Set<String> certificateStatusList = mCertificateStatusManager.getByStudent(pStudent.getId())
        .stream()
        .filter(c -> c.getStatus().equals(CertificateStatus.Status.DELIVERED))
        .map(c -> c.getFeeCategoryId())
        .collect(Collectors.toSet());

    List<FeeCategory> feeCategories = new ArrayList<>();
    List<FeeCategory> allFeeCategories = mFeeCategoryManager.getAll();

    if (pStudent.getStatus().equals(StudentStatus.PASSED)) {
      for (FeeCategory feeCategory : pFeeCategories) {
        List<String> dependencies = new ArrayList<>();
        if (feeCategory.getDependencies() != null)
          dependencies = Arrays.asList(feeCategory.getDependencies().split(","));

        if ((dependencies.size() - 1) == certificateStatusList.size() && dependencies.get(0).equals("G"))
          feeCategories.add(feeCategory);
      }
      return feeCategories;
    } else if (pStudent.getStatus().equals(StudentStatus.ACTIVE)) {
      for (FeeCategory feeCategory : pFeeCategories) {
        List<String> dependencies = new ArrayList<>();
        boolean certificateForGraduate = false;
        if (feeCategory.getDependencies() != null) {
          dependencies = Arrays.asList(feeCategory.getDependencies().split(","));
          certificateForGraduate = dependencies.get(0).equals("G") ? true : false;
        }
        if (dependencies.size() == certificateStatusList.size() && !certificateForGraduate)
          feeCategories.add(feeCategory);
      }
      return feeCategories;
    } else return null;
  }
}
