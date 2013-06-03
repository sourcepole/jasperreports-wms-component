package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.ooxml.GenericElementDocxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterContext;

public class WmsMapElementDocxHandler implements GenericElementDocxHandler {

  private static final WmsMapElementDocxHandler INSTANCE = new WmsMapElementDocxHandler();

  public static WmsMapElementDocxHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRDocxExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell) {
    JRDocxExporter exporter = (JRDocxExporter) exporterContext.getExporter();
    try {
      exporter.exportImage(exporterContext.getTableHelper(),
          getImage(exporterContext, element), gridCell);
    } catch (JRException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

  @Override
  public JRPrintImage getImage(JRDocxExporterContext exporterContext,
      JRGenericPrintElement element) throws JRException
  {
    try {
      return WmsMapElementImageProvider.getImage(
          exporterContext.getJasperReportsContext(), element);
    } catch (IOException e) {
      throw new JRException(e);
    }
  }

}
