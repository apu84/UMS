package org.ums.fee.semesterfee;

import org.ums.decorator.ContentDaoDecorator;

public class SemesterAdmissionStatusDaoDecorator extends
    ContentDaoDecorator<SemesterAdmissionStatus, MutableSemesterAdmissionStatus, Long, SemesterAdmissionStatusManager>
    implements SemesterAdmissionStatusManager {
}
