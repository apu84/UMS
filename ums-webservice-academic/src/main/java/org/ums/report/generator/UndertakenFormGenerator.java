package org.ums.report.generator;

import com.itextpdf.text.DocumentException;
import org.ums.enums.ProgramType;

import java.io.IOException;
import java.io.OutputStream;

public interface UndertakenFormGenerator {

  void createUndertakenForm(ProgramType pProgramType, int pSemesterId, String pReceiptId, OutputStream pOutputStream)
      throws IOException, DocumentException;
}
