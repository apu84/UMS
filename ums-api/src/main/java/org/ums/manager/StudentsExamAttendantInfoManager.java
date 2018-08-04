package org.ums.manager;

import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public interface StudentsExamAttendantInfoManager extends
    ContentManager<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo, Long> {

  List<StudentsExamAttendantInfo> getSemesterExamTypeDateWiseRecords(final Integer pSemesterId,
      final Integer pExamType, final String pExamDate);
}
