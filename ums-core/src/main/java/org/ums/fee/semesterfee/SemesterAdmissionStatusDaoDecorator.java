package org.ums.fee.semesterfee;

import org.ums.decorator.ContentDaoDecorator;

import java.util.Optional;

public class SemesterAdmissionStatusDaoDecorator extends
    ContentDaoDecorator<SemesterAdmissionStatus, MutableSemesterAdmissionStatus, Long, SemesterAdmissionStatusManager>
    implements SemesterAdmissionStatusManager {
  @Override
  public Optional<SemesterAdmissionStatus> getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return getManager().getAdmissionStatus(pStudentId, pSemesterId);
  }

  @Override
  public Optional<SemesterAdmissionStatus> lastAdmissionStatus(String pStudentId) {
    return getManager().lastAdmissionStatus(pStudentId);
  }
}
