package org.ums.punishment.penalty;

import org.ums.decorator.ContentDaoDecorator;

public class PenaltyDaoDecorator extends ContentDaoDecorator<Penalty, MutablePenalty, Long, PenaltyManager> implements
    PenaltyManager {
}
