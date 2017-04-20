package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;

public class MutableDistrictResource extends Resource {

  @Autowired
  ResourceHelper<District, MutableDistrict, String> mDistrictResourceHelper;

}
