package org.ums.ems.report;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface EmployeeCVGenerator {
  void createEmployeeCV(String pEmployeeId, OutputStream pOutputStream) throws IOException, DocumentException;
}
