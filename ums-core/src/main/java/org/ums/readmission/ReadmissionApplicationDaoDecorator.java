package org.ums.readmission;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class ReadmissionApplicationDaoDecorator extends
    ContentDaoDecorator<ReadmissionApplication, MutableReadmissionApplication, Long, ReadmissionApplicationManager>
    implements ReadmissionApplicationManager {

  @Override
  public List<ReadmissionApplication> getReadmissionApplications(Integer pSemesterId) {
    return getManager().getReadmissionApplications(pSemesterId);
  }

  @Override
  public List<ReadmissionApplication> getReadmissionApplication(Integer pSemesterId, String pStudentId) {
    return getManager().getReadmissionApplication(pSemesterId, pStudentId);
  }
}
