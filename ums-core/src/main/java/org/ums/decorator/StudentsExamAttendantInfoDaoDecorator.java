package org.ums.decorator;

import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.manager.StudentsExamAttendantInfoManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public class StudentsExamAttendantInfoDaoDecorator
    extends
    ContentDaoDecorator<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo, Long, StudentsExamAttendantInfoManager>
    implements StudentsExamAttendantInfoManager {

  @Override
  public List<StudentsExamAttendantInfo> getSemesterExamTypeDateWiseRecords(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return getManager().getSemesterExamTypeDateWiseRecords(pSemesterId, pExamType, pExamDate);
  }
}
