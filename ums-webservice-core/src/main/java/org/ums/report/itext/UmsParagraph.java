package org.ums.report.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class UmsParagraph extends Paragraph {

  public UmsParagraph() {
    super();
  }

  public UmsParagraph(String string) {
    super(string);
    this.spacingBefore = 4f;
  }

  public UmsParagraph(String string, Font font) {
    super(string, font);
    this.spacingBefore = 25F;
  }

  public UmsParagraph(Chunk chunk) {
    super(chunk);
    this.spacingBefore = 4f;
  }
}
