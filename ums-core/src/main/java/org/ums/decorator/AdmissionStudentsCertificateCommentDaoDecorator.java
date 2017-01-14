package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;
import org.ums.manager.AdmissionStudentsCertificateCommentManager;

import java.util.List;

/**
 * Created by kawsu on 1/12/2017.
 */
public class AdmissionStudentsCertificateCommentDaoDecorator
    extends
    ContentDaoDecorator<AdmissionStudentsCertificateComment, MutableAdmissionStudentsCertificateComment, Integer, AdmissionStudentsCertificateCommentManager>
    implements AdmissionStudentsCertificateCommentManager {
  @Override
  public List<AdmissionStudentsCertificateComment> getComments(int pSemesterId, String pReceiptId) {
    return getManager().getComments(pSemesterId, pReceiptId);
  }

  @Override
  public int saveComment(
      MutableAdmissionStudentsCertificateComment pMutableAdmissionStudentsCertificateComment) {
    return getManager().saveComment(pMutableAdmissionStudentsCertificateComment);
  }
}
