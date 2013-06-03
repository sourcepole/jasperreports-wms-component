package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.oasis.GenericElementOdtHandler;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporterContext;

public class WmsMapElementOdtHandler implements GenericElementOdtHandler {

  private static final WmsMapElementOdtHandler INSTANCE = new WmsMapElementOdtHandler();

  public static WmsMapElementOdtHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JROdtExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell) {
    try {
      JROdtExporter exporter = (JROdtExporter) exporterContext.getExporter();
      exporter.exportImage(exporterContext.getTableBuilder(),
          getImage(exporterContext, element), gridCell);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JRPrintImage getImage(JROdtExporterContext exporterContext,
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
