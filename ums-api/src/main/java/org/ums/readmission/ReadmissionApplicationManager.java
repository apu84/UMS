package org.ums.readmission;

import java.util.List;

import org.ums.manager.ContentManager;

public interface ReadmissionApplicationManager extends
    ContentManager<ReadmissionApplication, MutableReadmissionApplication, Long> {
  List<ReadmissionApplication> getReadmissionApplications(Integer pSemesterId);

  List<ReadmissionApplication> getReadmissionApplication(Integer pSemesterId, String pStudentId);
}
