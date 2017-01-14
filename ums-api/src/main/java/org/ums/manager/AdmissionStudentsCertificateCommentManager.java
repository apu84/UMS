package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;

import java.util.List;

/**
 * Created by kawsu on 1/12/2017.
 */
public interface AdmissionStudentsCertificateCommentManager
    extends
    ContentManager<AdmissionStudentsCertificateComment, MutableAdmissionStudentsCertificateComment, Integer> {

  List<AdmissionStudentsCertificateComment> getComments(final int pSemesterId,
      final String pReceiptId);

  int saveComment(
      final MutableAdmissionStudentsCertificateComment pMutableAdmissionStudentsCertificateComment);
}
