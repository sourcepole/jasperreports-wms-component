package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImage;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.export.GenericElementXmlHandler;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterContext;

/**
 * WMS map element handler to produce XML report output.
 */
public class WmsMapElementXmlHandler implements GenericElementXmlHandler {

  private static final WmsMapElementXmlHandler INSTANCE = new WmsMapElementXmlHandler();

  public static WmsMapElementXmlHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRXmlExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRXmlExporter exporter = (JRXmlExporter) exporterContext.getExporter();
      exporter.exportImage(getImage(exporterContext.getJasperReportsContext(),
          element));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
