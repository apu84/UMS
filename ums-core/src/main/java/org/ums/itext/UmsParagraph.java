package org.ums.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class UmsParagraph extends Paragraph {

  public UmsParagraph() {
    super();
  }

  public UmsParagraph(String string) {
    super(string);
    super.spacingBefore = 4f;
    super.setExtraParagraphSpace(4f);
  }

  public UmsParagraph(String string, Font font) {
    super(string, font);
    super.spacingBefore = 4f;
    super.setExtraParagraphSpace(4f);
  }

  public UmsParagraph(Chunk chunk) {
    super(chunk);
    super.spacingBefore = 4f;
    super.setExtraParagraphSpace(4f);
  }
}
