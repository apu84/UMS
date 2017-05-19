package org.ums.fee.certificate;

import org.ums.decorator.ContentDaoDecorator;

public class CertificateStatusDaoDecorator
    extends
    ContentDaoDecorator<CertificateStatus, MutableCertificateStatus, Long, CertificateStatusManager>
    implements
    CertificateStatusManager {
}