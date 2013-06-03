package com.sourcepole.jasperreports.wmsmap;

import static com.sourcepole.jasperreports.wmsmap.WmsMapRequest.mapRequest;
import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXB;

import net.sf.jasperreports.components.map.MapPrintElement;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sourcepole.jasperreports.wmsmap.error.ServiceException;
import com.sourcepole.jasperreports.wmsmap.error.ServiceExceptionReport;

/**
 * Utility class to create a {@link JRPrintImage} for a WMS map generic element.
 */
public class WmsMapElementImageProvider {

  private static Log log = LogFactory
      .getLog(WmsMapElementImageProvider.class);
  private static Logger logger = Logger
      .getLogger(WmsMapElementImageProvider.class.getName());

  public static JRPrintImage getImage(
      JasperReportsContext jasperReportsContext, JRGenericPrintElement element)
      throws JRException, IOException {
    log.info("Processing map element: " + element.getKey());

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

    // FIXMEMAP there are no scale image, alignment and onError attributes
    // defined for the map element
    printImage.setScaleImage(ScaleImageEnum.CLIP);
    printImage.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
    printImage.setVerticalAlignment(VerticalAlignEnum.TOP);

    Map<String, Object> elementParameters = WmsMapElementImageProvider
        .elementParameters(element);
    WmsMapRequest mapRequest = mapRequest(element.getKey(), element.getWidth(),
        element.getHeight(), elementParameters);

    Renderable cacheRenderer = (Renderable) element
        .getParameterValue(MapPrintElement.PARAMETER_CACHE_RENDERER);

    if (cacheRenderer == null) {
      URL mapUrl = mapRequest.toMapUrl();

      String message = "Loading map for element '" + element.getKey()
          + "' from URL: " + mapUrl;
      if (log.isInfoEnabled()) {
        log.info(message);
      }
      if (logger.isLoggable(Level.INFO)) {
        logger.log(Level.INFO, message);
      }

      HttpURLConnection httpConnection = (HttpURLConnection) mapUrl
          .openConnection();
      // Handle XML response
      validateServerResponse(mapRequest, mapUrl, httpConnection);

      cacheRenderer = RenderableUtil.getInstance(jasperReportsContext)
          .getRenderable(mapUrl, OnErrorTypeEnum.ERROR);
      cacheRenderer.getImageData(jasperReportsContext);
      element.setParameterValue(MapPrintElement.PARAMETER_CACHE_RENDERER,
          cacheRenderer);
    }

    printImage.setRenderable(cacheRenderer);

    return printImage;
  }

  static void validateServerResponse(WmsMapRequest request, URL mapUrl,
      HttpURLConnection httpConnection)
      throws IOException, JRException {
    String contentType = httpConnection.getContentType();
    if (contentType == null) {
      throw new JRException(format("Unable to determine content type, URL: %s",
          mapUrl));
    }
    if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
      if (contentType.contains("xml")) {
        evaluateServiceExceptionReport(request, mapUrl, httpConnection);
        return;
      }
      throw new JRException(format("Unable to load WMS map for report element:"
          + " %s, server responded: %s, URL: %s", request.getElementName(),
          httpConnection.getResponseMessage(), mapUrl));
    }
    if (contentType.contains("xml")) {
      evaluateServiceExceptionReport(request, mapUrl, httpConnection);
    }
  }

  static void evaluateServiceExceptionReport(WmsMapRequest request, URL mapUrl,
      HttpURLConnection httpConnection)
      throws IOException, JRException {
    InputStream inputStream = httpConnection.getInputStream();
    ServiceExceptionReport exceptionReport = JAXB.unmarshal(inputStream,
        ServiceExceptionReport.class);
    if (exceptionReport == null
        || exceptionReport.getServiceException() == null) {
      completeServiceExceptionReport(request, mapUrl, httpConnection);
    }
    ServiceException ex = exceptionReport.getServiceException();
    throw new JRException(format("Server reported an exception for "
        + "map element '%s', code: %s, message: %s, URL: %s",
        request.getElementName(), ex.getCode(), ex.getBody(), mapUrl));
  }

  private static void completeServiceExceptionReport(WmsMapRequest request,
      URL mapUrl, HttpURLConnection httpConnection) {
    // TODO Auto-generated method stub

  }

  static Map<String, Object> elementParameters(JRGenericPrintElement element) {
    Map<String, Object> parameters = new HashMap<String, Object>();
    Set<String> parameterNames = element.getParameterNames();
    for (String parameterName : parameterNames) {
      parameters.put(parameterName, element.getParameterValue(parameterName));
    }
    return parameters;
  }
}
