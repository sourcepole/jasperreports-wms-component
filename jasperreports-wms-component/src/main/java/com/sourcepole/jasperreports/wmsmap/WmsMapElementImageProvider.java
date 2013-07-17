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

import static com.sourcepole.jasperreports.wmsmap.error.ServiceExceptionReport.parseExceptionReportFromStream;
import static java.lang.String.format;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.Renderable;
import net.sf.jasperreports.engine.RenderableUtil;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;

import com.sourcepole.jasperreports.wmsmap.error.ServiceException;
import com.sourcepole.jasperreports.wmsmap.error.ServiceExceptionReport;

/**
 * Utility class to create a {@link JRPrintImage} for a WMS map generic element.
 */
public class WmsMapElementImageProvider {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  public static JRPrintImage getImage(
      JasperReportsContext jasperReportsContext, JRGenericPrintElement element)
      throws JRException, IOException {

    JRBasePrintImage printImage = new JRBasePrintImage(
        element.getDefaultStyleProvider());

    printImage.setUUID(element.getUUID());
    printImage.setX(element.getX());
    printImage.setY(element.getY());
    printImage.setWidth(element.getWidth());
    printImage.setHeight(element.getHeight());
    printImage.setStyle(element.getStyle());
    printImage.setMode(element.getModeValue());
    printImage.setBackcolor(element.getBackcolor());
    printImage.setForecolor(element.getForecolor());
    printImage.setLazy(false);
    printImage.setScaleImage(ScaleImageEnum.CLIP);
    printImage.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
    printImage.setVerticalAlignment(VerticalAlignEnum.TOP);

    Renderable cacheRenderer = (Renderable) element
        .getParameterValue(WmsMapPrintElement.PARAMETER_CACHE_RENDERER);

    if (cacheRenderer == null) {
      cacheRenderer = getImageRenderable(jasperReportsContext, element);
      element.setParameterValue(WmsMapPrintElement.PARAMETER_CACHE_RENDERER,
          cacheRenderer);
    }

    printImage.setRenderable(cacheRenderer);

    return printImage;
  }

  public static Renderable getImageRenderable(
      JasperReportsContext jasperReportsContext, JRGenericPrintElement element)
      throws MalformedURLException, IOException, JRException {
    WmsRequestBuilder requestBuilder = mapRequestBuilder(element);
    return getImageRenderable(jasperReportsContext, element.getKey(),
        requestBuilder);
  }

  public static Renderable getImageRenderable(
      JasperReportsContext jasperReportsContext, String elementName,
      WmsRequestBuilder requestBuilder)
      throws MalformedURLException,
      IOException, JRException {

    URL mapUrl = requestBuilder.toMapUrl();
    HttpURLConnection httpConnection = (HttpURLConnection) mapUrl
        .openConnection();

    // Handle XML response
    validateServerResponse(elementName, mapUrl, httpConnection);

    JasperReportsContext context = jasperReportsContext;
    if (context == null) {
      context = DefaultJasperReportsContext.getInstance();
    }
    Renderable cacheRenderer = RenderableUtil.getInstance(context)
        .getRenderable(
            httpConnection.getInputStream(),
            OnErrorTypeEnum.ERROR);
    // cacheRenderer.getImageData(jasperReportsContext);
    return cacheRenderer;
  }

  static void validateServerResponse(String elementName, URL mapUrl,
      HttpURLConnection httpConnection)
      throws IOException, JRException {
    String contentType = httpConnection.getContentType();
    if (contentType == null) {
      throw new JRException(format("Unable to determine content type, URL: %s",
          mapUrl));
    }
    if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
      if (contentType.contains("xml")) {
        evaluateServiceExceptionReport(elementName, mapUrl, httpConnection);
        return;
      }
      throw new JRException(format("Unable to load WMS map for report element:"
          + " %s, server responded: %s, URL: %s", elementName,
          httpConnection.getResponseMessage(), mapUrl));
    }
    if (contentType.contains("xml")) {
      evaluateServiceExceptionReport(elementName, mapUrl, httpConnection);
    }
  }

  static void evaluateServiceExceptionReport(String elementName, URL mapUrl,
      HttpURLConnection httpConnection)
      throws IOException, JRException {
    String encodingName = httpConnection.getContentEncoding();
    Charset encoding;
    try {
      encoding = Charset.forName(encodingName);
    } catch (IllegalArgumentException e) {
      encoding = UTF_8;
    }
    ServiceExceptionReport report = parseExceptionReportFromStream(
        httpConnection.getInputStream(), encoding);
    ServiceException ex = report.getServiceException();
    throw new JRException(format("The server reported an exception for "
        + "WMS map element '%s', code: %s, message: %s, URL: %s",
        elementName, ex.getCode(), ex.getBody(), mapUrl));
  }

  static WmsRequestBuilder mapRequestBuilder(JRGenericPrintElement element) {
    Map<WmsRequestParameter, String> params = new HashMap<WmsRequestParameter, String>();
    for (WmsRequestParameter param : WmsRequestParameter.values()) {
      Object value = element.getParameterValue(param.name());
      if (value != null) {
        params.put(param, value.toString());
      }
    }
    WmsRequestBuilder requestBuilder = WmsRequestBuilder
        .createGetMapRequest(params)
        .width(element.getWidth())
        .height(element.getHeight());
    return requestBuilder;
  }

}
