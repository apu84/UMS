package org.ums.lock;

import org.ums.decorator.ContentDaoDecorator;

public class LockDaoDecorator extends ContentDaoDecorator<Lock, MutableLock, String, LockManager> implements
    LockManager {
}
