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
    public void printEmployeeList(String pDeptList, String pEmpTypeList, OutputStream pOutputStream) throws IOException, DocumentException {

        List<Employee> employeeList = mEmployeeManager.downloadEmployeeList(pDeptList, pEmpTypeList);

        List<Department> departmentList = mDepartmentManager.getAll();

        departmentList.sort(Comparator.comparing(Department::getId));

        Document document = new Document();
        document.addTitle("Meeting Minutes");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
        Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
        Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
        Font fontTimes16Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);

        document.open();
        document.setPageSize(PageSize.A4);

        Paragraph paragraph = null;
        Chunk chunk = null;

        chunk = new Chunk("Ahsanullah University of Science & Technology", fontTimes16Bold);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        chunk = new Chunk("141-142, Love Road, Tejgaon I/A, Dhaka-1208", fontTimes11Bold);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        emptyLine(paragraph, 1);
        document.add(paragraph);

        chunk = new Chunk("List of Employees", fontTimes14Bold);
        chunk.setUnderline(1.0f, -2.3f);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        emptyLine(paragraph, 1);
        document.add(paragraph);

        int k = 0;

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setTotalWidth(new float[]{30, 50, 150, 120, 100});

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

        cell = new PdfPCell(new Phrase("Department"));
        table.addCell(cell);

        for (int j = 0; j < employeeList.size(); j++) {
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

            cell =
                    new PdfPCell(new Phrase(mDepartmentManager.get(employeeList.get(j).getDepartment().getId())
                            .getShortName()));
            table.addCell(cell);

        }
        document.add(table);

        document.close();
        baos.writeTo(pOutputStream);
    }

    void emptyLine(Paragraph p, int number) {
        for (int i = 0; i < number; i++) {
            p.add(new Paragraph(" "));
        }
    }
}
