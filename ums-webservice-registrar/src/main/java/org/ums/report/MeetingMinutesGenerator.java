package org.ums.report;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface MeetingMinutesGenerator {

  void createMeetingMinutes(int pMeetingType, int pMeetingNo, int pPrintingType, OutputStream pOutputStream)
      throws IOException, DocumentException;
}
