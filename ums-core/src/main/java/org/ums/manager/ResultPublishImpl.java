package org.ums.manager;

import org.ums.decorator.ResultPublishDaoDecorator;
import org.ums.domain.model.mutable.MutableResultPublish;
import org.ums.persistent.model.PersistentResultPublish;

public class ResultPublishImpl extends ResultPublishDaoDecorator {
  @Override
  public void publishResult(Integer programId, Integer semesterId) {
    MutableResultPublish resultPublish = new PersistentResultPublish();
    resultPublish.setProgramId(programId);
    resultPublish.setSemesterId(semesterId);
    resultPublish.create();
  }
}
