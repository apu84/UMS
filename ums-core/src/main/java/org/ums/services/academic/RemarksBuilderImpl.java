package org.ums.services.academic;

import java.util.List;

import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.CourseType;

public class RemarksBuilderImpl implements RemarksBuilder {
  @Override
  public String getGradeSheetRemarks(List<UGRegistrationResult> pResults, StudentRecord.Status pStatus,
      Integer pSemesterId) {
    String passFailStatus = null, carryOverText = null;
    if(pStatus == StudentRecord.Status.FAILED) {
      passFailStatus = "Failed";
      carryOverText = getCarryOverText(pResults, pSemesterId, false);
    }
    else if(pStatus == StudentRecord.Status.PASSED) {
      passFailStatus = "Passed";
      carryOverText = getCarryOverText(pResults, pSemesterId, true);
    }
    return String.format("%s %s %s", passFailStatus, StringUtils.isEmpty(carryOverText) ? "" : "with carry over in",
        carryOverText);
  }

  @Override
  public String getTabulationSheetRemarks(List<UGRegistrationResult> pResults, StudentRecord pStudentRecord,
      Integer pSemesterId) {
    String promotionStatus = null, carryOverText = null;
    if(pStudentRecord.getStatus() == StudentRecord.Status.FAILED) {
      promotionStatus = "Failed ";
      carryOverText = getCarryOverText(pResults, pSemesterId, false);
    }
    else if(pStudentRecord.getStatus() == StudentRecord.Status.PASSED) {
      promotionStatus = promotionText(pStudentRecord);
      carryOverText = getCarryOverText(pResults, pSemesterId, true);
    }
    return String.format("%s %s %s", promotionStatus, StringUtils.isEmpty(carryOverText) ? "" : "with carry over in",
        carryOverText);
  }

  private String getCarryOverText(List<UGRegistrationResult> pResults, final Integer pSemesterId,
      final boolean isSemesterPassed) {
    StringBuilder builder = new StringBuilder();

    for(UGRegistrationResult result : pResults) {
      if(result.getCourse().getCourseType() == CourseType.THEORY) {
        if(result.getSemesterId().intValue() != pSemesterId) {
          if(result.getGradeLetter().equalsIgnoreCase("F")) {
            buildRemarks(builder, result);
          }
        }
        else {
          if(!isPassed(result, pResults, pSemesterId)) {
            if(isSemesterPassed) {
              buildRemarks(builder, result);
            }
            else if(result.getType() == CourseRegType.CARRY) {
              buildRemarks(builder, result);
            }
          }

        }
      }
    }
    return builder.toString();
  }

  private void buildRemarks(final StringBuilder pStringBuilder, UGRegistrationResult pResult) {
    if(pStringBuilder.length() > 0) {
      pStringBuilder.append(", ");
    }
    pStringBuilder.append(pResult.getCourse().getNo());
  }

  private boolean isPassed(final UGRegistrationResult pResult, final List<UGRegistrationResult> pResults,
      final Integer pSemesterId) {
    if(!pResult.getGradeLetter().equalsIgnoreCase("F")) {
      return true;
    }
    else {
      for(UGRegistrationResult result : pResults) {
        if(result.getCourseId().equalsIgnoreCase(pResult.getCourseId())
            && result.getSemesterId().intValue() == pSemesterId && !result.getGradeLetter().equalsIgnoreCase("F")) {
          return true;
        }
      }
    }
    return false;
  }

  private String promotionText(StudentRecord pStudentRecord) {
    if(pStudentRecord.getStudent().getProgram().getShortName().equalsIgnoreCase("BSc in ARC")) {
      if(pStudentRecord.getYear() == 5 && pStudentRecord.getAcademicSemester() == 2) {
        return "Completed Fifth Year Second Semester";
      }
    }
    else {
      if(pStudentRecord.getYear() == 4 && pStudentRecord.getAcademicSemester() == 2) {
        return "Completed Fourth Year Second Semester";
      }
    }
    return incrementYearSemester(pStudentRecord);
  }

  private String incrementYearSemester(StudentRecord pStudentRecord) {
    String[] year = {"", "First", "Second", "Third", "Fourth", "Fifth"};
    String[] academicSemester = {"Second", "First"};
    return String.format("Promoted to %s Year %s Semester",
        year[pStudentRecord.getYear() + ((pStudentRecord.getAcademicSemester() + 1) % 2)],
        academicSemester[(pStudentRecord.getAcademicSemester() + 1) % 2]);
  }
}
