package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImage;

import java.awt.Graphics2D;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementGraphics2DHandler;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterContext;
import net.sf.jasperreports.engine.export.draw.ImageDrawer;
import net.sf.jasperreports.engine.export.draw.Offset;

/**
 * WMS map element handler to produce {@link Graphics2D} report output as used
 * in iReport preview.
 */
public class WmsMapElementGraphics2DHandler implements
    GenericElementGraphics2DHandler {

  private static final WmsMapElementGraphics2DHandler INSTANCE =
      new WmsMapElementGraphics2DHandler();

  public static WmsMapElementGraphics2DHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRGraphics2DExporterContext context,
      JRGenericPrintElement element, Graphics2D grx, Offset offset) {
    try {
      JRGraphics2DExporter exporter = (JRGraphics2DExporter) context
          .getExporter();
      ImageDrawer imageDrawer = exporter.getFrameDrawer().getDrawVisitor()
          .getImageDrawer();
      JRPrintImage image = getImage(context.getJasperReportsContext(), element);
      imageDrawer.draw(grx, image, offset.getX(), offset.getY());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
