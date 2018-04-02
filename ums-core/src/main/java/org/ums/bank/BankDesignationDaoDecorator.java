package org.ums.bank;

import org.ums.decorator.ContentDaoDecorator;

public class BankDesignationDaoDecorator extends
    ContentDaoDecorator<BankDesignation, MutableBankDesignation, Long, BankDesignationManager> implements
    BankDesignationManager {
}
