package org.ums.util;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * Created by Ifti on 13-Nov-16.
 */
public class ReportUtils {

  // PdfPCell Types
  /**
   * Cell - Border Zero and Padding Zero
   */
  public static final String B0_P0 = "B0_P0";
  /**
   * Cell-Border Zero, Top-Bottom Padding Zero and Left-Right Padding 10px
   */
  public static final String B0_TBP0_LRP10 = "B0_TBP0_LRP10";

  /**
   * Cell-Border Zero, Top-Bottom Padding Zero and Left-Right Padding 10px
   */
  public static final String B0_LP10 = "B0_TBP0_LP10";

  // Chunk Types
  /**
   * Chunk - 3 Spaces in Left and Right Side
   */
  public static final String LR3S = "LR3S";
  public static final String LR1S = "LR1S";

  public static Chunk getChunk(String content, Font font, String chunkType) {
    Chunk chunk = null;
    switch(chunkType) {
      case LR3S:
        chunk = new Chunk(new Chunk("\u00a0\u00a0\u00a0" + content + "\u00a0\u00a0\u00a0", font));
        break;
      case LR1S:
        chunk = new Chunk(new Chunk("\u00a0" + content + "\u00a0", font));
        break;
    }
    return chunk;
  }

  public static PdfPCell getCell(String cellType) {
    PdfPCell cell = new PdfPCell();
    switch(cellType) {
      case B0_P0:
        cell.setPadding(0f);
        cell.setBorder(Rectangle.NO_BORDER);
        break;
      case B0_LP10:
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(0f);
        cell.setPaddingLeft(10f);
        cell.setPaddingRight(0f);
        cell.setBorder(Rectangle.NO_BORDER);
        break;
      case B0_TBP0_LRP10:
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(0f);
        cell.setPaddingLeft(10f);
        cell.setPaddingRight(10f);
        cell.setBorder(Rectangle.NO_BORDER);
        break;
    }
    return cell;
  }
}
