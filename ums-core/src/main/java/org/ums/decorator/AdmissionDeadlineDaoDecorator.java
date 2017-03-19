package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionDeadline;
import org.ums.domain.model.mutable.MutableAdmissionDeadline;
import org.ums.manager.AdmissionDeadlineManager;

/**
 * Created by Monjur-E-Morshed on 29-Dec-16.
 */
public class AdmissionDeadlineDaoDecorator extends
    ContentDaoDecorator<AdmissionDeadline, MutableAdmissionDeadline, Integer, AdmissionDeadlineManager> implements
    AdmissionDeadlineManager {

}
