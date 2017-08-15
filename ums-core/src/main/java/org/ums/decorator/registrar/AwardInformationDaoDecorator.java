package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.manager.registrar.AwardInformationManager;

import java.util.List;

public class AwardInformationDaoDecorator extends
    ContentDaoDecorator<AwardInformation, MutableAwardInformation, Long, AwardInformationManager> implements
    AwardInformationManager {

  @Override
  public int saveAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    return getManager().saveAwardInformation(pMutableAwardInformation);
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(String pEmployeeId) {
    return getManager().getEmployeeAwardInformation(pEmployeeId);
  }

  @Override
  public int deleteAwardInformation(String pEmployeeId) {
    return getManager().deleteAwardInformation(pEmployeeId);
  }

  @Override
  public int updateAwardInformation(List<MutableAwardInformation> pAwardInformation) {
    return getManager().updateAwardInformation(pAwardInformation);
  }

  @Override
  public int deleteAwardInformation(List<MutableAwardInformation> pAwardInformation) {
    return getManager().deleteAwardInformation(pAwardInformation);
  }
}
