package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.manager.AdmissionCommentForStudentManager;

import java.util.List;

public class AdmissionCommentForStudentDaoDecorator
    extends
    ContentDaoDecorator<AdmissionCommentForStudent, MutableAdmissionCommentForStudent, Integer, AdmissionCommentForStudentManager>
    implements AdmissionCommentForStudentManager {
  @Override
  public List<AdmissionCommentForStudent> getComments(int pSemesterId, String pReceiptId) {
    return getManager().getComments(pSemesterId, pReceiptId);
  }

  @Override
  public int saveComment(MutableAdmissionCommentForStudent pMutableAdmissionStudentsCertificateComment) {
    return getManager().saveComment(pMutableAdmissionStudentsCertificateComment);
  }
}
