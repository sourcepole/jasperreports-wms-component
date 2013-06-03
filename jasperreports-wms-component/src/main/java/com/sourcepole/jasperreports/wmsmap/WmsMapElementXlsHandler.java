package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImage;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementXlsHandler;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRGridLayout;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterContext;

public class WmsMapElementXlsHandler implements GenericElementXlsHandler {
  private static final WmsMapElementXlsHandler INSTANCE = new WmsMapElementXlsHandler();

  public static WmsMapElementXlsHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRXlsExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell, int colIndex,
      int rowIndex, int emptyCols, int yCutsRow, JRGridLayout layout
      ) {
    try {
      JRXlsExporter exporter = (JRXlsExporter) exporterContext.getExporter();
      JRPrintImage image = getImage(exporterContext.getJasperReportsContext(),
          element);
      exporter.exportImage(image, gridCell, colIndex, rowIndex, emptyCols,
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
