package org.ums.punishment.offence;

import org.ums.decorator.ContentDaoDecorator;

public class OffenceDaoDecorator extends ContentDaoDecorator<Offence, MutableOffence, Long, OffenceManager> implements
    OffenceManager {
}
