package org.ums.transaction;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.manager.ContentManager;

public class ContentTransaction<R, M, I, C extends ContentManager<R, M, I>> extends ContentDaoDecorator<R, M, I, C> {
}
