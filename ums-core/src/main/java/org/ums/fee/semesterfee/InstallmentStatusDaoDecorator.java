package org.ums.fee.semesterfee;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class InstallmentStatusDaoDecorator extends
    ContentDaoDecorator<InstallmentStatus, MutableInstallmentStatus, Long, InstallmentStatusManager> implements
    InstallmentStatusManager {
  @Override
  public List<InstallmentStatus> getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    return getManager().getInstallmentStatus(pStudentId, pSemesterId);
  }
}
