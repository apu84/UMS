package org.ums.report.generator;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface UndertakenFormGenerator {

  void createUndertakenForm(String pProgramType, int pSemesterId, String pReceiptId,
      OutputStream pOutputStream) throws IOException, DocumentException;
}
