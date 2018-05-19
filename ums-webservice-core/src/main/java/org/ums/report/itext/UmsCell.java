package org.ums.report.itext;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UmsCell extends PdfPCell {

  private ColumnText column;
  private int verticalAlignment;
  private float paddingLeft;
  private float paddingRight;
  private float paddingTop;
  private float paddingBottom;
  private float fixedHeight;
  private float calculatedHeight;
  private float minimumHeight;
  private float cachedMaxHeight;
  private boolean noWrap;
  private PdfPTable table;
  private int colspan;
  private int rowspan;
  private Image image;
  private PdfPCellEvent cellEvent;
  private boolean useDescender;
  private boolean useBorderPadding;
  protected Phrase phrase;
  private int rotation;
  protected PdfName role;
  protected HashMap<PdfName, PdfObject> accessibleAttributes;
  protected AccessibleElementId id;
  protected ArrayList<PdfPHeaderCell> headers;

  public UmsCell() {
    super();
    this.column = new ColumnText((PdfContentByte) null);
    this.verticalAlignment = 4;
    this.paddingLeft = 20.0F;
    this.paddingRight = 2.0F;
    this.paddingTop = 2.0F;
    this.paddingBottom = 2.0F;
    this.fixedHeight = 0.0F;
    this.calculatedHeight = 0.0F;
    this.noWrap = false;
    this.colspan = 1;
    this.rowspan = 1;
    this.useDescender = false;
    this.useBorderPadding = false;
    this.role = PdfName.TD;
    this.accessibleAttributes = null;
    this.id = new AccessibleElementId();
    this.headers = null;
    this.borderWidth = 0.5F;
    this.border = 15;
    this.column.setLeading(0.0F, 1.0F);
  }

  public UmsCell(Phrase phrase) {
    super(phrase);
    this.paddingLeft = 120.0f;
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
