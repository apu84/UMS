package org.ums.decorator;

import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.manager.QuestionCorrectionManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class QuestionCorrectionInfoDaoDecorator extends
    ContentDaoDecorator<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long, QuestionCorrectionManager>
    implements QuestionCorrectionManager {
  @Override
  public List<QuestionCorrectionInfo> getInfoBySemesterIdAndExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getInfoBySemesterIdAndExamType(pSemesterId, pExamType);
  }
}
