package org.ums.report.generator.certificates;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Created by Monjur-E-Morshed on 14-Sep-17.
 */
public interface CharacterCertificate {
  public void generateCharacterCertificate(String pStudentId) throws IOException, DocumentException;
}
