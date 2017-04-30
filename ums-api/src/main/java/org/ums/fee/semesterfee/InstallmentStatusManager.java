package org.ums.fee.semesterfee;

import org.ums.manager.ContentManager;

import java.util.List;

public interface InstallmentStatusManager extends ContentManager<InstallmentStatus, MutableInstallmentStatus, Long> {
  List<InstallmentStatus> getInstallmentStatus(String pStudentId, Integer pSemesterId);
}
