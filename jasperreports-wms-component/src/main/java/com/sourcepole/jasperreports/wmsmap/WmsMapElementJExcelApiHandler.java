package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImage;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.export.GenericElementJExcelApiHandler;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterContext;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRGridLayout;

/**
 * JExcel handler for WmsMap elements.
 */
public class WmsMapElementJExcelApiHandler implements
    GenericElementJExcelApiHandler {

  /** Singleton instance. */
  private static final WmsMapElementJExcelApiHandler INSTANCE =
      new WmsMapElementJExcelApiHandler();

  public static WmsMapElementJExcelApiHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JExcelApiExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell,
      int colIndex, int rowIndex, int emptyCols, int yCutsRow,
      JRGridLayout layout) {
    try {
      JExcelApiExporter exporter = (JExcelApiExporter) exporterContext
          .getExporter();
      JasperReportsContext reportsContext = exporterContext
          .getJasperReportsContext();
      JRPrintImage printImage = getImage(reportsContext, element);
      exporter.exportImage(printImage, gridCell, colIndex, rowIndex, emptyCols,
          yCutsRow, layout);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
