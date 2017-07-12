package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.solr.indexer.model.MutableIndex;

public interface MutableAreaOfInterestInformation extends AreaOfInterestInformation, Editable<String>,
    MutableIdentifier<String>, MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setAreaOfInterest(final AreaOfInterest pAreaOfInterest);

  void setAreaOfInterestId(final Integer pAreaOfInterestId);
}
