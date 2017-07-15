package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.registrar.ServiceInformationDetailManager;

public class ServiceInformationDetailDaoDecorator
    extends
    ContentDaoDecorator<ServiceInformationDetail, MutableServiceInformationDetail, Integer, ServiceInformationDetailManager>
    implements ServiceInformationDetailManager {

}
