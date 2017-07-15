package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableServiceInformationDetail extends ServiceInformationDetail, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {
}
