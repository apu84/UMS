package org.ums.punishment;

import org.ums.decorator.ContentDaoDecorator;

public class PunishmentDaoDecorator extends ContentDaoDecorator<Punishment, MutablePunishment, Long, PunishmentManager>
    implements PunishmentManager {
}
