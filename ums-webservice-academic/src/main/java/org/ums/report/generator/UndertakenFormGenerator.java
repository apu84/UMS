package org.ums.report.generator;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface UndertakenFormGenerator {

  void createUndertakenForm(OutputStream pOutputStream) throws IOException, DocumentException;
}
