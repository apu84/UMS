package org.ums.accesscontrol;


import org.ums.cache.ContentDaoDecorator;
import org.ums.manager.ContentManager;

public class ContentAccessControl<R, M, I, C extends ContentManager<R, M, I>> extends ContentDaoDecorator<R, M, I, C> {
}
