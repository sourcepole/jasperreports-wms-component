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

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.ooxml.GenericElementDocxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterContext;

/**
 * WMS map element handler to produce DOCX report output.
 */
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
