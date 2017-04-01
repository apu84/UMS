package org.ums.fee.semesterfee;

import org.ums.decorator.ContentDaoDecorator;

public class InstallmentStatusDaoDecorator extends
    ContentDaoDecorator<InstallmentStatus, MutableInstallmentStatus, Long, InstallmentStatusManager> implements
    InstallmentStatusManager {
}
