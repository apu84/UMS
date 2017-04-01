package org.ums.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

@Component
public class UGFeeGeneratorImpl implements UGFeeGenerator {
  @Autowired
  UGLateFeeManager mUGLateFeeManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  UGFeeManager mUGFeeManager;

  @Override
  public List<UGFee> calculateSemesterFee(String pStudentId, Integer pSemesterId) {
    Student student = mStudentManager.get(pStudentId);
    Integer facultyId = student.getProgram().getFaculty().getId();
    List<UGFee> fees = mUGFeeManager.getFee(facultyId, pSemesterId);
    List<UGLateFee> lateFees = mUGLateFeeManager.getLateFees(pSemesterId);
    List<UGFee> studentFee = new ArrayList<>();
    for(UGFee fee : fees) {
      if(fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ADMISSION.toString())
          || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ESTABLISHMENT.toString())
          || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString())
          || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.LABORATORY.toString())) {
        studentFee.add(fee);
      }
    }

    Date today = new Date();
    for(UGLateFee lateFee : lateFees) {
      if(lateFee.getFrom().before(today) && lateFee.getTo().after(today)) {
        studentFee.add(toUGFee(lateFee, pSemesterId, facultyId));
      }
    }
    return studentFee;
  }

  private UGFee toUGFee(UGLateFee pLateFee, Integer pSemesterId, Integer pFacultyId) {
    return new UGFee() {
      @Override
      public String getFeeCategoryId() {
        return FeeCategory.Categories.LATE_FEE.toString();
      }

      @Override
      public FeeCategory getFeeCategory() {
        return null;
      }

      @Override
      public Integer getSemesterId() {
        return pSemesterId;
      }

      @Override
      public Semester getSemester() {
        return null;
      }

      @Override
      public Integer getFacultyId() {
        return pFacultyId;
      }

      @Override
      public Faculty getFaculty() {
        return null;
      }

      @Override
      public Double getAmount() {
        return (double) pLateFee.getFee();
      }

      @Override
      public MutableUGFee edit() {
        return null;
      }

      @Override
      public Long getId() {
        return null;
      }

      @Override
      public String getLastModified() {
        return null;
      }
    };
  }
}
