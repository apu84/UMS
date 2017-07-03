package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.common.MutableNationality;
import org.ums.manager.common.NationalityManager;

public class NationalityDaoDecorator extends
    ContentDaoDecorator<Nationality, MutableNationality, Integer, NationalityManager> implements NationalityManager {
}
