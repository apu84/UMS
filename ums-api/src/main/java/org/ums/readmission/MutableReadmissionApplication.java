package org.ums.readmission;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableReadmissionApplication extends ReadmissionApplication, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setAppliedOn(Date pAppliedOn);

  void setVerifiedOn(Date pVerifiedOn);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);

  void setCourse(Course pCourse);

  void setCourseId(String pCourseId);

  void setApplicationStatus(ReadmissionApplication.Status pApplicationStatus);
}
