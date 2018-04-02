package org.ums.bank;

import org.ums.decorator.ContentDaoDecorator;

public class BankDaoDecorator extends ContentDaoDecorator<Bank, MutableBank, String, BankManager> implements
    BankManager {
}
