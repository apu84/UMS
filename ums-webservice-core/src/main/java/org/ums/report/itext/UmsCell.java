package org.ums.report.itext;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UmsCell extends PdfPCell {

  public UmsCell() {
    super();
    super.setPaddingLeft(4.0f);
    super.setPaddingBottom(3.0f);
  }

  public UmsCell(Phrase phrase) {
    super(phrase);
    super.setPaddingLeft(4.0f);
    super.setPaddingBottom(3.0f);
  }

  public UmsCell(Image image) {
    super(image);
  }

  public UmsCell(Image image, boolean fit) {
    super(image, fit);
  }

  public UmsCell(PdfPTable table) {
    super(table);
  }

  @Override
  public void addElement(Element element) {
    super.addElement(element);
  }
}
