package org.ums.manager;

import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public interface QuestionCorrectionManager extends
    ContentManager<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long> {
  List<QuestionCorrectionInfo> getInfoBySemesterIdAndExamType(final Integer pSemesterId, final Integer pExamType);

}
