package org.ums.common.report.generator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Monjur-E-Morshed on 20-Dec-16.
 */

@Component
public class AdmissionStudentGeneratorImpl implements AdmissionStudentGenerator {
  @Override
  public void createABlankTaletalkDataFormatFile(OutputStream pOutputStream, int pSemesterId)
      throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Workbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet("taletalk data sample format");
    Row row = sheet.createRow((short) 0);
    Cell cell = row.createCell(0);
    cell.setCellValue("receipt_id");
    row.createCell(1).setCellValue("pin");
    row.createCell(2).setCellValue("hsc_board");
    row.createCell(3).setCellValue("hsc_roll");
    row.createCell(4).setCellValue("hsc_regno");
    row.createCell(5).setCellValue("hsc_year");
    row.createCell(6).setCellValue("hsc_group");
    row.createCell(7).setCellValue("ssc_board");
    row.createCell(8).setCellValue("ssc_roll");
    row.createCell(9).setCellValue("ssc_regno");
    row.createCell(10).setCellValue("ssc_year");
    row.createCell(11).setCellValue("ssc_group");
    row.createCell(12).setCellValue("gender");
    row.createCell(13).setCellValue("date_of_birth");
    row.createCell(14).setCellValue("student_name");
    row.createCell(15).setCellValue("father_name");
    row.createCell(16).setCellValue("mother_name");
    row.createCell(17).setCellValue("ssc_gpa");
    row.createCell(18).setCellValue("hsc_gpa");
    row.createCell(19).setCellValue("quota");

    wb.write(pOutputStream);

    baos.writeTo(pOutputStream);

  }
}
