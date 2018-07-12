package org.ums.employee.cv;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmployeeManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

@Component
public class EmployeeListGeneratorImpl implements EmployeeListGenerator {

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private DesignationManager mDesignationManager;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  private PersonalInformationManager mPersonalInformationManager;

  @Override
  public void printEmployeeList(OutputStream pOutputStream) throws IOException, DocumentException {

    List<Employee> employeeList = mEmployeeManager.getAll();

    List<Department> departmentList = mDepartmentManager.getAll();

    departmentList.sort(Comparator.comparing(Department::getId));

    Document document = new Document();
    document.addTitle("Meeting Minutes");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = null;
    Chunk chunk = null;

    int k = 0;

    for(int i = 0; i < departmentList.size(); i++) {
      PdfPTable table = new PdfPTable(4);
      table.setWidthPercentage(100);
      table.setTotalWidth(new float[] {30, 50, 120, 120});
      chunk = new Chunk("Department: " + departmentList.get(i).getLongName());
      paragraph = new Paragraph(chunk);
      document.add(paragraph);

      paragraph = new Paragraph();
      emptyLine(paragraph, 1);
      document.add(paragraph);
      k = 0;

      PdfPCell cell = new PdfPCell(new Phrase("#"));
      table.addCell(cell);

      cell = new PdfPCell(new Phrase("Employee Id"));
      table.addCell(cell);

      cell = new PdfPCell(new Phrase("Employee Name"));
      table.addCell(cell);

      cell = new PdfPCell(new Phrase("Designation"));
      table.addCell(cell);

      for(int j = 0; j < employeeList.size(); j++) {
        if(departmentList.get(i).getId().equals(employeeList.get(j).getDepartment().getId()) && employeeList.get(j).getEmployeeType() != 1) {
          k++;
          cell = new PdfPCell(new Phrase(k + "."));
          table.addCell(cell);

          cell = new PdfPCell(new Phrase(employeeList.get(j).getId()));
          table.addCell(cell);

          cell = new PdfPCell(new Phrase(mPersonalInformationManager.get(employeeList.get(j).getId()).getName()));
          table.addCell(cell);

          cell =
              new PdfPCell(new Phrase(mDesignationManager.get(employeeList.get(j).getDesignationId())
                  .getDesignationName()));
          table.addCell(cell);
        }
      }
      document.add(table);
      document.newPage();
    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }
}
