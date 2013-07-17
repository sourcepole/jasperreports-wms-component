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

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementPdfHandler;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterContext;

/**
 * WMS map element handler to produce PDF report output.
 */
public class WmsMapElementPdfHandler implements GenericElementPdfHandler {

  private static final WmsMapElementPdfHandler INSTANCE = new WmsMapElementPdfHandler();

  public static WmsMapElementPdfHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRPdfExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRPdfExporter exporter = (JRPdfExporter) exporterContext.getExporter();
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
