package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;

import java.io.Serializable;

public interface ServiceInformationDetail extends Serializable, EditType<MutableServiceInformationDetail>,
    Identifier<Integer>, LastModifier {

}
