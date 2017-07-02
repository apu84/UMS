package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;
import org.ums.manager.common.DistrictManager;

public class DistrictDaoDecorator extends ContentDaoDecorator<District, MutableDistrict, Integer, DistrictManager>
    implements DistrictManager {
}
