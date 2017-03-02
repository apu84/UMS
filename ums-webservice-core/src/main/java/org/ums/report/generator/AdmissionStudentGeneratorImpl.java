package org.ums.report.generator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Dec-16.
 */

@Component
public class AdmissionStudentGeneratorImpl implements AdmissionStudentGenerator {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private AdmissionStudentManager mAdmissionStudentManager;

  @Autowired
  private ProgramManager mProgramManager;

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
    row.createCell(9).setCellValue("ssc_year");
    row.createCell(10).setCellValue("ssc_group");
    row.createCell(11).setCellValue("gender");
    row.createCell(12).setCellValue("date_of_birth");
    row.createCell(13).setCellValue("student_name");
    row.createCell(14).setCellValue("father_name");
    row.createCell(15).setCellValue("mother_name");
    row.createCell(16).setCellValue("ssc_gpa");
    row.createCell(17).setCellValue("hsc_gpa");
    row.createCell(18).setCellValue("quota");
    row.createCell(19).setCellValue("unit");

    wb.write(pOutputStream);
    baos.writeTo(pOutputStream);

  }

  @Override
  public void createABlankMeritListUploadFormatFile(OutputStream pOutputStream, int pSemesterId)
      throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Workbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet("Merit list data sample format");
    Row row = sheet.createRow((short) 0);
    row.createCell(0).setCellValue("merit sl no.");
    row.createCell(1).setCellValue("receipt id");
    row.createCell(2).setCellValue("admission roll");

    wb.write(pOutputStream);

    baos.writeTo(pOutputStream);
  }

  @Override
  public void createBlankMigrationListUploadFormatFile(OutputStream pOutputStream) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Workbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet("Migration List Upload");
    Row row = sheet.createRow((short) 0);
    row.createCell(0).setCellValue("receipt_id");

    wb.write(pOutputStream);

    baos.writeTo(pOutputStream);
  }

  @Override
  public void createMeritListXlsFile(int pSemesterId, ProgramType pProgramType,
      QuotaType pQuotaType, String pUnit, OutputStream pOutputStream) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Workbook wb = new HSSFWorkbook();

    Sheet sheet =
        wb.createSheet("Admission Merit List " + mSemesterManager.get(pSemesterId).getName());
    Row row = sheet.createRow((short) 0);
    row.createCell(0).setCellValue("Merit Sl No.");
    row.createCell(1).setCellValue("Receipt Id");
    row.createCell(2).setCellValue("Candidate's Name");
    row.createCell(3).setCellValue("Selected Program");
    row.createCell(4).setCellValue("Waiting Program");
    row.createCell(5).setCellValue("Allocated Program");

    List<AdmissionStudent> students =
        mAdmissionStudentManager.getMeritList(pSemesterId, pQuotaType, pUnit, pProgramType);

    for(int i = 1; i <= students.size(); i++) {
      AdmissionStudent student = students.get(i - 1);
      Row stdRow = sheet.createRow((short) i);
      stdRow.createCell(0).setCellValue(student.getMeritSerialNo().toString());
      stdRow.createCell(1).setCellValue(student.getReceiptId());
      stdRow.createCell(2).setCellValue(student.getStudentName());
      if(student.getProgramIdByTransfer() != null) {
        stdRow.createCell(3).setCellValue(student.getProgramByMerit().getShortName());
      }
      else {
        stdRow.createCell(3).setCellValue(" ");
      }
      if(student.getProgramIdByTransfer() != null) {
        stdRow.createCell(4).setCellValue(student.getProgramByTransfer().getShortName());
      }
      else {
        stdRow.createCell(4).setCellValue(" ");

      }
      if(student.getAllocatedProgramId() != null) {
        row.createCell(5).setCellValue(student.getAllocatedProgram().getShortName());
      }
      else {
        stdRow.createCell(5).setCellValue(" ");
      }
    }

    wb.write(pOutputStream);

    baos.writeTo(pOutputStream);
  }
}
