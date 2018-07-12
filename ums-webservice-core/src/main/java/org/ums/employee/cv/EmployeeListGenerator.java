package org.ums.employee.cv;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface EmployeeListGenerator {

  void printEmployeeList(OutputStream pOutputStream) throws IOException, DocumentException;
}
