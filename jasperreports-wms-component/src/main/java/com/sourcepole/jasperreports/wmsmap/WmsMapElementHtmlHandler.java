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

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.export.GenericElementHtmlHandler;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterContext;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.util.JRColorUtil;
import net.sf.jasperreports.web.util.VelocityUtil;

/**
 * WMS Map element handler rendering HTML output.
 */
public class WmsMapElementHtmlHandler implements GenericElementHtmlHandler {

  private static final WmsMapElementHtmlHandler INSTANCE = new WmsMapElementHtmlHandler();

  private static final String MAP_ELEMENT_HTML_TEMPLATE =
      "com/sourcepole/jasperreports/wmsmap/resources/templates/WmsMapElementTemplate.vm";

  public static WmsMapElementHtmlHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public String getHtmlFragment(JRHtmlExporterContext context,
      JRGenericPrintElement element) {

    Map<String, Object> contextMap = new HashMap<String, Object>();
    contextMap.put("mapCanvasId", "map_canvas_" + element.hashCode());

    if (context.getExporter() instanceof JRXhtmlExporter) {
      contextMap.put("xhtml", "xhtml");
      JRXhtmlExporter xhtmlExporter = (JRXhtmlExporter) context.getExporter();
      contextMap.put("elementX", xhtmlExporter.toSizeUnit(element.getX()));
      contextMap.put("elementY", xhtmlExporter.toSizeUnit(element.getY()));
    } else {
      JRHtmlExporter htmlExporter = (JRHtmlExporter) context.getExporter();
      contextMap.put("elementX", htmlExporter.toSizeUnit(element.getX()));
      contextMap.put("elementY", htmlExporter.toSizeUnit(element.getY()));
    }
    contextMap.put("elementId", element.getKey());
    contextMap.put("elementWidth", element.getWidth());
    contextMap.put("elementHeight", element.getHeight());

    if (element.getModeValue() == ModeEnum.OPAQUE) {
      contextMap.put("backgroundColor",
          JRColorUtil.getColorHexa(element.getBackcolor()));
    }
    WmsRequestBuilder requestBuilder =
        WmsMapElementImageProvider.mapRequestBuilder(element);
    try {
      contextMap.put("wmsMapUrl", requestBuilder.toMapUrl());
    } catch (MalformedURLException e) {
      throw new RuntimeException("Unable to build WMS map service url", e);
    }

    return VelocityUtil.processTemplate(MAP_ELEMENT_HTML_TEMPLATE, contextMap);
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

}
