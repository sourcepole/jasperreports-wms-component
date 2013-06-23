package com.sourcepole.jasperreports.wmsmap.fill;

import static com.sourcepole.jasperreports.wmsmap.WmsMapElementImageProvider.getImageRenderable;
import static com.sourcepole.jasperreports.wmsmap.WmsRequestBuilder.createGetMapRequest;

import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.Renderable;
import net.sf.jasperreports.engine.component.BaseFillComponent;
import net.sf.jasperreports.engine.component.FillPrepareResult;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.fill.JRTemplateGenericElement;
import net.sf.jasperreports.engine.fill.JRTemplateGenericPrintElement;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

import com.sourcepole.jasperreports.wmsmap.WmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.WmsMapPrintElement;
import com.sourcepole.jasperreports.wmsmap.WmsRequestBuilder;
import com.sourcepole.jasperreports.wmsmap.WmsRequestParameter;

public class WmsMapFillComponent extends BaseFillComponent {

  JRFillObjectFactory factory;

  private final WmsMapComponent mapComponent;

  private String bbox;
  private String layers;
  private String styles;
  private String urlParameters;
  private String imageType;
  private String wmsServiceUrl;
  private Boolean transparent;
  private String wmsVersion;
  private String srsCrs;

  private Renderable imageRenderable;

  public WmsMapFillComponent(WmsMapComponent map) {
    this.mapComponent = map;
  }

  public WmsMapFillComponent(WmsMapComponent map, JRFillObjectFactory factory) {
    this.mapComponent = map;
    this.factory = factory;
  }

  protected WmsMapComponent getMap() {
    return mapComponent;
  }

  @Override
  public void evaluate(byte evaluation) throws JRException {
    if (isEvaluateNow()) {
      evaluateMap(evaluation);
    }
  }

  void evaluateMap(byte evaluation)
      throws JRException {
    String elementName = fillContext.getComponentElement().getKey();
    int width = fillContext.getComponentElement().getWidth();
    int height = fillContext.getComponentElement().getHeight();
    wmsServiceUrl = mapComponent.getWmsServiceUrl();
    wmsVersion = mapComponent.getWmsVersion();
    srsCrs = mapComponent.getSrs();
    transparent = mapComponent.getTransparent();
    bbox = (String) fillContext.evaluate(mapComponent.getBBoxExpression(),
        evaluation);
    layers = (String) fillContext.evaluate(mapComponent.getLayersExpression(),
        evaluation);
    styles = (String) fillContext.evaluate(mapComponent.getStylesExpression(),
        evaluation);
    urlParameters = (String) fillContext.evaluate(
        mapComponent.getUrlParametersExpression(), evaluation);
    imageType = mapComponent.getImageType();

    WmsRequestBuilder requestBuilder = createGetMapRequest(wmsServiceUrl);
    requestBuilder
        .version(wmsVersion)
        .srsCrs(srsCrs)
        .transparent(transparent)
        .boundingBox(bbox)
        .layers(layers)
        .styles(styles)
        .format(imageType)
        .urlParameters(urlParameters)
        .width(width)
        .height(height);

    // load map image
    try {
      this.imageRenderable = getImageRenderable(null, elementName,
          requestBuilder);
    } catch (MalformedURLException e) {
      throw new JRException(e.getMessage(), e);
    } catch (IOException e) {
      throw new JRException(e.getMessage(), e);
    } catch (IllegalStateException e) {
      throw new JRException(e.getMessage(), e);
    }
  }

  protected boolean isEvaluateNow() {
    return mapComponent.getEvaluationTime() == EvaluationTimeEnum.NOW;
  }

  @Override
  public FillPrepareResult prepare(int availableHeight) {
    return FillPrepareResult.PRINT_NO_STRETCH;
  }

  @Override
  public JRPrintElement fill() {
    JRComponentElement element = fillContext.getComponentElement();
    JRTemplateGenericElement template = new JRTemplateGenericElement(
        fillContext.getElementOrigin(),
        fillContext.getDefaultStyleProvider(),
        WmsMapPrintElement.WMS_MAP_ELEMENT_TYPE);
    template = deduplicate(template);

    JRTemplateGenericPrintElement printElement = new JRTemplateGenericPrintElement(
        template, elementId);
    printElement.setUUID(element.getUUID());
    printElement.setX(element.getX());
    printElement.setY(fillContext.getElementPrintY());
    printElement.setWidth(element.getWidth());
    printElement.setHeight(element.getHeight());

    if (isEvaluateNow()) {
      copy(printElement);
    } else {
      fillContext.registerDelayedEvaluation(printElement,
          mapComponent.getEvaluationTime(), mapComponent.getEvaluationGroup());
    }

    return printElement;
  }

  @Override
  public void evaluateDelayedElement(JRPrintElement element, byte evaluation)
      throws JRException {
    evaluateMap(evaluation);
    copy((JRGenericPrintElement) element);
  }

  protected void copy(JRGenericPrintElement printElement) {
    printElement.setParameterValue(
        WmsRequestParameter.WMS_URL.name(), wmsServiceUrl);
    printElement.setParameterValue(WmsRequestParameter.VERSION.name(),
        wmsVersion);
    printElement.setParameterValue(WmsRequestParameter.SRS_CRS.name(),
        srsCrs);
    printElement.setParameterValue(WmsRequestParameter.TRANSPARENT.name(),
        transparent);
    printElement.setParameterValue(WmsRequestParameter.BBOX.name(), bbox);
    printElement.setParameterValue(WmsRequestParameter.LAYERS.name(),
        layers);
    if (styles != null) {
      printElement.setParameterValue(WmsRequestParameter.STYLE.name(),
          styles);
    }
    if (imageType != null) {
      printElement.setParameterValue(WmsRequestParameter.FORMAT.name(),
          imageType);
    }
    if (urlParameters != null) {
      printElement.setParameterValue(
          WmsRequestParameter.URL_PARAMETERS.name(), urlParameters);
    }
    if (this.imageRenderable != null) {
      printElement.setParameterValue(
          WmsMapPrintElement.PARAMETER_CACHE_RENDERER, this.imageRenderable);
    }
  }
}
