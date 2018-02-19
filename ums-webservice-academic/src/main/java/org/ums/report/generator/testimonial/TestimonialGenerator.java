package org.ums.report.generator.testimonial;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

public interface TestimonialGenerator {

  void createTestimonial(String pStudentId, OutputStream pOutputStream) throws IOException, DocumentException;
}
