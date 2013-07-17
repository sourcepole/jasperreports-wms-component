package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.ooxml.GenericElementXlsxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporterContext;

/**
 * WMS map element handler to produce XLSX report output.
 */
public class WmsMapElementXlsxHandler implements GenericElementXlsxHandler {
  private static final WmsMapElementXlsxHandler INSTANCE = new WmsMapElementXlsxHandler();

  public static WmsMapElementXlsxHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRXlsxExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell, int colIndex,
      int rowIndex) {
    try {
      JRXlsxExporter exporter = (JRXlsxExporter) exporterContext.getExporter();
      JRPrintImage image = getImage(exporterContext, element);
      exporter.exportImage(image, gridCell,
          colIndex, rowIndex, 0, 0, null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

  @Override
  public JRPrintImage getImage(JRXlsxExporterContext exporterContext,
      JRGenericPrintElement element) throws JRException {
    try {
      return WmsMapElementImageProvider
          .getImage(exporterContext.getJasperReportsContext(), element);
    } catch (IOException e) {
      throw new JRException(e);
    }
  }
}
