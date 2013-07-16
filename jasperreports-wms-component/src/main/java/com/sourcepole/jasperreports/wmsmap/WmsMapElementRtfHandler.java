package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementRtfHandler;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporterContext;

public class WmsMapElementRtfHandler implements GenericElementRtfHandler {

  private static final WmsMapElementRtfHandler INSTANCE = new WmsMapElementRtfHandler();

  public static WmsMapElementRtfHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRRtfExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRRtfExporter exporter = (JRRtfExporter) exporterContext.getExporter();
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
