package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;

import java.util.List;

public interface AdmissionCommentForStudentManager extends
    ContentManager<AdmissionCommentForStudent, MutableAdmissionCommentForStudent, Integer> {

  List<AdmissionCommentForStudent> getComments(final int pSemesterId, final String pReceiptId);

  int saveComment(final MutableAdmissionCommentForStudent pMutableAdmissionStudentsCertificateComment);
}
