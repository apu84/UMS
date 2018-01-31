package org.ums.report.generator.testimonial;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.formatter.DateFormat;
import org.ums.manager.EmployeeManager;
import org.ums.manager.StudentManager;
import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TestimonialGeneratorImpl implements TestimonialGenerator {

  @Autowired
  DateFormat mDateFormat;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionManager;

  @Override
  public void createTestimonial(String pStudentId, OutputStream pOutputStream) throws IOException, DocumentException {

    mDateFormat = new DateFormat("dd MMM YYYY");

    Student student = mStudentManager.get(pStudentId);
    User user = null;
    Employee employee = null;
    Employee deptHead = null;

    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionManager.getAll();

    for(AdditionalRolePermissions additionalRolePermissions1 : additionalRolePermissions) {
      user = mUserManager.get(additionalRolePermissions1.getUserId());
      employee = mEmployeeManager.get(user.getEmployeeId());
      if(student.getDepartment().getId().equals(employee.getDepartment().getId()))
        deptHead = employee;
    }

    Document document = new Document();
    document.addTitle("Testimonial");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = null;
    Chunk chunk = null;

    paragraph = new Paragraph();
    emptyLine(paragraph, 9);
    document.add(paragraph);

    chunk = new Chunk("TESTIMONIAL");
    chunk.setUnderline(1.0f, -2.3f);

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes14Bold);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    chunk = new Chunk("Date: " + mDateFormat.format(new Date()));
    chunk.setFont(fontTimes11Normal);
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    emptyLine(paragraph, 2);
    document.add(paragraph);

    paragraph = new Paragraph("This is to certify that ");
    paragraph.add(new Chunk(student.getFullName(), fontTimes11Bold));
    paragraph.add(" son/daughter of ");
    paragraph.add(new Chunk(student.getFatherName(), fontTimes11Bold));
    paragraph.add(" and ");
    paragraph.add(new Chunk(student.getMotherName(), fontTimes11Bold));
    paragraph.add(", bearing student ID no. ");
    paragraph.add(new Chunk(student.getId(), fontTimes11Bold));
    paragraph.add(" was a student of ");
    paragraph.add(new Chunk(student.getDepartment().getLongName(), fontTimes11Bold));
    paragraph.add(", of the Ahsanullah University of Science and Technology. " + "He/She successfully completed ");
    paragraph.add(new Chunk(student.getDepartmentId().equals("02") ? "4- " : "4/5- "));
    paragraph.add("year program for the degree of ");
    paragraph.add(new Chunk(student.getProgram().getLongName(), fontTimes11Bold));
    paragraph.add(" from this University on ");
    paragraph.add(new Chunk("Some date ", fontTimes11Bold));
    paragraph.add("with a CGPA of ");
    paragraph.add(new Chunk("3.99 ", fontTimes11Bold));
    paragraph.add("on a scale of 4.00");

    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    paragraph = new Paragraph("The medium of instruction of this University is English ");
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    paragraph =
        new Paragraph(
            "I found him/her a sincere student. His/her conduct and academic performance in the class were satisfactory.");
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    paragraph = new Paragraph("I wish him/her every success in life");
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    emptyLine(paragraph, 10);
    document.add(paragraph);

    paragraph = new Paragraph("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    document.add(paragraph);

    paragraph = new Paragraph(deptHead.getPersonalInformation().getFullName());
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    document.add(paragraph);

    paragraph = new Paragraph("Head, " + deptHead.getDepartment().getLongName());
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    document.add(paragraph);

    document.close();
    baos.writeTo(pOutputStream);

  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }
}
