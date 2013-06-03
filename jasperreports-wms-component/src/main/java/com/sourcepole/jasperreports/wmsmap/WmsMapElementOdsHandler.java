package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.oasis.GenericElementOdsHandler;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporterContext;

/**
 * ODS hander for WmsMap elements.
 */
public class WmsMapElementOdsHandler implements GenericElementOdsHandler {

  /** Singleton instance. */
  private static final WmsMapElementOdsHandler INSTANCE = new WmsMapElementOdsHandler();

  public static WmsMapElementOdsHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JROdsExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell) {
    try {
      JROdsExporter exporter = (JROdsExporter) exporterContext.getExporter();
      exporter.exportImage(exporterContext.getTableBuilder(),
          getImage(exporterContext, element), gridCell);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JRPrintImage getImage(JROdsExporterContext exporterContext,
      JRGenericPrintElement element) throws JRException {
    try {
      return WmsMapElementImageProvider.getImage(
          exporterContext.getJasperReportsContext(), element);
    } catch (IOException e) {
      throw new JRException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
