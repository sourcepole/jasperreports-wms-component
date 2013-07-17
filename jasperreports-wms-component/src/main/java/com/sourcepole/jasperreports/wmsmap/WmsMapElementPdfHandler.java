package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementPdfHandler;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterContext;

/**
 * WMS map element handler to produce PDF report output.
 */
public class WmsMapElementPdfHandler implements GenericElementPdfHandler {

  private static final WmsMapElementPdfHandler INSTANCE = new WmsMapElementPdfHandler();

  public static WmsMapElementPdfHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRPdfExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRPdfExporter exporter = (JRPdfExporter) exporterContext.getExporter();
      JRPrintImage image = WmsMapElementImageProvider.getImage(
          exporterContext.getJasperReportsContext(), element);
      exporter.exportImage(image);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
