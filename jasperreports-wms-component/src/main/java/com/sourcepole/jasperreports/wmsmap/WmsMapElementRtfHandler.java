/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementRtfHandler;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporterContext;

public class WmsMapElementRtfHandler implements GenericElementRtfHandler {

  private static final WmsMapElementRtfHandler INSTANCE = new WmsMapElementRtfHandler();

  public static WmsMapElementRtfHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRRtfExporterContext exporterContext,
      JRGenericPrintElement element) {
    try {
      JRRtfExporter exporter = (JRRtfExporter) exporterContext.getExporter();
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
