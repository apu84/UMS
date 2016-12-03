package org.ums.services;

import org.springframework.stereotype.Component;
import org.ums.enums.CourseRegType;
import org.ums.exceptions.ValidationException;

@Component("utilsService")
public class UtilsService {

  public String getGradeLetter(final Integer pTotalMarks, CourseRegType regType) {
    if(pTotalMarks >= 80 && regType == CourseRegType.REGULAR)
      return "A+";
    else if(pTotalMarks >= 75 && pTotalMarks < 80 && regType == CourseRegType.REGULAR)
      return "A";
    else if(pTotalMarks >= 70 && pTotalMarks < 75 && regType == CourseRegType.REGULAR)
      return "A-";
    else if(pTotalMarks >= 65 && pTotalMarks < 70 && regType == CourseRegType.REGULAR)
      return "B+";
    else if(pTotalMarks >= 60 && pTotalMarks < 65 && regType == CourseRegType.REGULAR)
      return "B";
    else if(pTotalMarks >= 60 && regType == CourseRegType.IMPROVEMENT)
      return "B";
    else if(pTotalMarks >= 55 && pTotalMarks < 60
        && (regType == CourseRegType.REGULAR || regType == CourseRegType.IMPROVEMENT))
      return "B-";
    else if(pTotalMarks >= 50 && pTotalMarks < 55
        && (regType == CourseRegType.REGULAR || regType == CourseRegType.IMPROVEMENT))
      return "C+";
    else if(pTotalMarks >= 45 && pTotalMarks < 50)
      return "C";
    else if(pTotalMarks >= 45
        && (regType == CourseRegType.CLEARANCE || regType == CourseRegType.CARRY || regType == CourseRegType.SPECIAL_CARRY))
      return "C";
    else if(pTotalMarks >= 40 && pTotalMarks < 45)
      return "D";
    else if(pTotalMarks < 40)
      return "F";
    else
      throw new ValidationException("Invalid Marks");
  }

}
