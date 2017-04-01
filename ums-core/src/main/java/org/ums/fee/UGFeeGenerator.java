package org.ums.fee;

import java.util.List;

public interface UGFeeGenerator {
  List<UGFee> calculateSemesterFee(String pStudentId, Integer pSemesterId);
}
