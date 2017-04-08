package org.ums.fee.semesterfee;

import org.ums.decorator.ContentDaoDecorator;

public class SemesterAdmissionStatusDaoDecorator extends
    ContentDaoDecorator<SemesterAdmissionStatus, MutableSemesterAdmissionStatus, Long, SemesterAdmissionStatusManager>
    implements SemesterAdmissionStatusManager {
  @Override
  public SemesterAdmissionStatus getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return getManager().getAdmissionStatus(pStudentId, pSemesterId);
  }
}
