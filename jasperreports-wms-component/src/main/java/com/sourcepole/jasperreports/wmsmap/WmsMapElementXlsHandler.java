/*
 * JasperReports/iReport WMS Component
 * 
 * Copyright (C) 2013 Sourcepole AG
 * 
 * JasperReports/iReport WMS Component is free software: you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * JasperReports/iReport WMS Component is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports/iReport WMS Component.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImage;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementXlsHandler;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRGridLayout;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterContext;

/**
 * WMS map element handler to produce XLS report output.
 */
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
