package com.sourcepole.jasperreports.wmsmap.fill;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.component.BaseFillComponent;
import net.sf.jasperreports.engine.component.FillPrepareResult;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.fill.JRTemplateGenericElement;
import net.sf.jasperreports.engine.fill.JRTemplateGenericPrintElement;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

import com.sourcepole.jasperreports.wmsmap.WmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.WmsMapPrintElement;
import com.sourcepole.jasperreports.wmsmap.WmsMapRequest;

public class WmsMapFillComponent extends BaseFillComponent {
  private final WmsMapComponent mapComponent;

  private String bbox;
  private String layers;
  private String styles;
  private String imageType;

  JRFillObjectFactory factory;

  private String wmsServiceUrl;

  private Boolean transparent;

  private String wmsVersion;

  private String srs;

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

  protected void evaluateMap(byte evaluation) throws JRException {
    wmsServiceUrl = mapComponent.getWmsServiceUrl();
    wmsVersion = mapComponent.getWmsVersion();
    srs = mapComponent.getSrs();
    transparent = mapComponent.getTransparent();

    bbox = (String) fillContext.evaluate(mapComponent.getBBoxExpression(),
        evaluation);
    layers = (String) fillContext.evaluate(mapComponent.getLayersExpression(),
        evaluation);
    styles = (String) fillContext.evaluate(mapComponent.getStylesExpression(),
        evaluation);

    if ("ERROR".equals(bbox)) {
      throw new JRException("Invalid bbox: " + bbox);
    }

    imageType = mapComponent.getImageType();
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
        WmsMapRequest.Parameter.WMS_URL.name(), wmsServiceUrl);
    printElement.setParameterValue(WmsMapRequest.Parameter.VERSION.name(),
        wmsVersion);
    printElement.setParameterValue(WmsMapRequest.Parameter.SRS.name(), srs);
    printElement.setParameterValue(WmsMapRequest.Parameter.TRANSPARENT.name(),
        transparent);
    printElement.setParameterValue(WmsMapRequest.Parameter.BBOX.name(), bbox);
    printElement.setParameterValue(WmsMapRequest.Parameter.LAYERS.name(),
        layers);
    if (styles != null) {
      printElement.setParameterValue(WmsMapRequest.Parameter.STYLE.name(),
          styles);
    }
    if (imageType != null) {
      printElement.setParameterValue(WmsMapRequest.Parameter.FORMAT.name(),
          imageType);
    }
  }
}
