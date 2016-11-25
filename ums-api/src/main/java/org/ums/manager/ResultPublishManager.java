package org.ums.manager;

import org.ums.domain.model.immutable.ResultPublish;
import org.ums.domain.model.mutable.MutableResultPublish;

public interface ResultPublishManager extends
    ContentManager<ResultPublish, MutableResultPublish, Integer> {
  void publishResult(final Integer pProgramId, final Integer pSemesterId) throws Exception;

  boolean isResultPublished(final Integer pProgramId, final Integer pSemesterId);
}
