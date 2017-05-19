package org.ums.fee.certificate;

public class CertificateStatusDao extends CertificateStatusDaoDecorator {
  String SELECT_ALL = "SELECT ID, STUDENT_ID, SEMESTER_ID, FEE_CATEGORY, TRANSACTION_ID, STATUS, PROCESSED_ON, USER FROM " +
      "CERTIFICATE_STATUS ";
}
