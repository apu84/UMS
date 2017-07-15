package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.ContentManager;

public interface ServiceInformationDetailManager extends
    ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Integer> {
}
