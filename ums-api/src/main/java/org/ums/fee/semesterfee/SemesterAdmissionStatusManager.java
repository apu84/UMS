package org.ums.fee.semesterfee;

import org.ums.manager.ContentManager;

import java.util.Optional;

public interface SemesterAdmissionStatusManager extends
    ContentManager<SemesterAdmissionStatus, MutableSemesterAdmissionStatus, Long> {
  Optional<SemesterAdmissionStatus> getAdmissionStatus(String pStudentId, Integer pSemesterId);

  Optional<SemesterAdmissionStatus> lastAdmissionStatus(String pStudentId);
}
