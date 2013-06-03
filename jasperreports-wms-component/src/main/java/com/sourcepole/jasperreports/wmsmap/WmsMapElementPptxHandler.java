package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.ooxml.GenericElementPptxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporterContext;

public class WmsMapElementPptxHandler implements GenericElementPptxHandler {

  private static final WmsMapElementPptxHandler INSTANCE = new WmsMapElementPptxHandler();

  public static WmsMapElementPptxHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRPptxExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRPptxExporter exporter = (JRPptxExporter) exporterContext.getExporter();
      exporter.exportImage(getImage(exporterContext, element));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

  @Override
  public JRPrintImage getImage(JRPptxExporterContext exporterContext,
      JRGenericPrintElement element) throws JRException {
    try {
      return WmsMapElementImageProvider.getImage(
          exporterContext.getJasperReportsContext(), element);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
