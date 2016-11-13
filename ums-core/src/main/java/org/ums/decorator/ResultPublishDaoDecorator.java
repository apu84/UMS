package org.ums.decorator;

import org.ums.domain.model.immutable.ResultPublish;
import org.ums.domain.model.mutable.MutableResultPublish;
import org.ums.manager.ResultPublishManager;

public class ResultPublishDaoDecorator extends
    ContentDaoDecorator<ResultPublish, MutableResultPublish, Integer, ResultPublishManager>
    implements ResultPublishManager {
  @Override
  public void publishResult(Integer programId, Integer semesterId) throws Exception {
    getManager().publishResult(programId, semesterId);
  }

  @Override
  public boolean isResultPublished(Integer pProgramId, Integer pSemesterId) {
    return getManager().isResultPublished(pProgramId, pSemesterId);
  }
}
