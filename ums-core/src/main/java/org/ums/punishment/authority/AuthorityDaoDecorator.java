package org.ums.punishment.authority;

import org.ums.decorator.ContentDaoDecorator;

public class AuthorityDaoDecorator extends ContentDaoDecorator<Authority, MutableAuthority, Long, AuthorityManager>
    implements AuthorityManager {
}
