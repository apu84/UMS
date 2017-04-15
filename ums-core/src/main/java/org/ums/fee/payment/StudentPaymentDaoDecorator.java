package org.ums.fee.payment;

import org.ums.decorator.ContentDaoDecorator;

public class StudentPaymentDaoDecorator extends
    ContentDaoDecorator<StudentPayment, MutableStudentPayment, Long, StudentPaymentManager> implements
    StudentPaymentManager {
}
