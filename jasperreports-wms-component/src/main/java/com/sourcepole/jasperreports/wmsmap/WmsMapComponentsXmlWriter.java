package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.component.ComponentXmlWriter;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.util.JRXmlWriteHelper;
import net.sf.jasperreports.engine.util.VersionComparator;
import net.sf.jasperreports.engine.util.XmlNamespace;
import net.sf.jasperreports.engine.xml.JRXmlBaseWriter;
import net.sf.jasperreports.engine.xml.JRXmlConstants;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

/**
 * XML writer for WMS map component implementation.
 * 
 * @see ComponentsExtensionsRegistryFactory
 */
public class WmsMapComponentsXmlWriter implements ComponentXmlWriter {

  static final String ATTRIBUTE_WMS_SERVICE_URL = "wmsServiceUrl";
  static final String ATTRIBUTE_WMS_VERSION = "wmsVersion";
  static final String ATTRIBUTE_SRS = "srs";
  static final String ATTRIBUTE_TRANSPARENT = "transparent";
  static final String ATTRIBUTE_IMAGE_TYPE = "imageType";

  public static final String PROPERTY_COMPONENTS_PREFIX = JRPropertiesUtil.PROPERTY_PREFIX
      + "components.";

  public static final String PROPERTY_COMPONENTS_VERSION_SUFFIX = ".version";

  private final JasperReportsContext jasperReportsContext;
  private final VersionComparator versionComparator;

  public WmsMapComponentsXmlWriter(JasperReportsContext jasperReportsContext) {
    this.jasperReportsContext = jasperReportsContext;
    this.versionComparator = new VersionComparator();
  }

  @Override
  public void writeToXml(JRComponentElement componentElement,
      JRXmlWriter reportWriter) throws IOException {
    Component component = componentElement.getComponent();
    if (component instanceof WmsMapComponent) {
      writeWmsMap(componentElement, reportWriter);
    }
  }

  protected void writeWmsMap(JRComponentElement componentElement,
      JRXmlWriter reportWriter) throws IOException {
    Component component = componentElement.getComponent();
    WmsMapComponent map = (WmsMapComponent) component;
    JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
    ComponentKey componentKey = componentElement.getComponentKey();

    XmlNamespace namespace = new XmlNamespace(
        ComponentsExtensionsRegistryFactory.NAMESPACE,
        componentKey.getNamespacePrefix(),
        ComponentsExtensionsRegistryFactory.XSD_LOCATION);

    writer.startElement("wmsmap", namespace);

    if (map.getEvaluationTime() != EvaluationTimeEnum.NOW) {
      writer.addAttribute(JRXmlConstants.ATTRIBUTE_evaluationTime,
          map.getEvaluationTime());
    }

    writer.addAttribute(JRXmlConstants.ATTRIBUTE_evaluationGroup,
        map.getEvaluationGroup());
    writer.addAttribute(ATTRIBUTE_WMS_SERVICE_URL, map.getWmsServiceUrl());
    writer.addAttribute(ATTRIBUTE_WMS_VERSION, map.getWmsVersion());
    writer.addAttribute(ATTRIBUTE_SRS, map.getSrs());
    writer.addAttribute(ATTRIBUTE_TRANSPARENT, map.getTransparent());
    writer.addAttribute(ATTRIBUTE_IMAGE_TYPE, map.getImageType().getName());

    writer.writeExpression("bboxExpression",
        map.getBBoxExpression());
    writer.writeExpression("layersExpression",
        map.getLayersExpression());
    writer.writeExpression("stylesExpression",
        map.getStylesExpression());

    writer.closeElement();
  }

  @Override
  public boolean isToWrite(JRComponentElement componentElement,
      JRXmlWriter reportWriter)
  {
    ComponentKey componentKey = componentElement.getComponentKey();
    if (ComponentsExtensionsRegistryFactory.NAMESPACE.equals(componentKey
        .getNamespace())) {
      if (ComponentsExtensionsRegistryFactory.WMS_MAP_COMPONENT_NAME
          .equals(componentKey.getName())) {
        return isNewerVersionOrEqual(componentElement, reportWriter,
            JRConstants.VERSION_5_0_1);
      }
    }
    // XXX: Defaults to true in jr ComponentsExtensionRegistryFactory???
    return false;
  }

  protected String getVersion(JRComponentElement componentElement,
      JRXmlWriter reportWriter) {
    String version = null;

    ComponentKey componentKey = componentElement.getComponentKey();
    String versionProperty = PROPERTY_COMPONENTS_PREFIX
        + componentKey.getName() + PROPERTY_COMPONENTS_VERSION_SUFFIX;

    if (componentElement.getPropertiesMap().containsProperty(versionProperty)) {
      version = componentElement.getPropertiesMap()
          .getProperty(versionProperty);
    } else {
      JRReport report = reportWriter.getReport();
      version = JRPropertiesUtil.getInstance(jasperReportsContext)
          .getProperty(report, versionProperty);

      if (version == null) {
        version = JRPropertiesUtil.getInstance(jasperReportsContext)
            .getProperty(report, JRXmlBaseWriter.PROPERTY_REPORT_VERSION);
      }
    }

    return version;
  }

  protected boolean isNewerVersionOrEqual(JRComponentElement componentElement,
      JRXmlWriter reportWriter, String oldVersion) {
    // FIXME VERSION can we pass something else then reportWriter?
    return versionComparator.compare(
        getVersion(componentElement, reportWriter), oldVersion) >= 0;
  }

  @SuppressWarnings("deprecation")
  protected void writeExpression(String name, JRExpression expression,
      boolean writeClass, JRComponentElement componentElement,
      JRXmlWriter reportWriter) throws IOException {
    JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
    if (isNewerVersionOrEqual(componentElement, reportWriter,
        JRConstants.VERSION_4_1_1)) {
      writer.writeExpression(name, expression);
    } else {
      writer.writeExpression(name, expression, writeClass);
    }
  }

  @SuppressWarnings("deprecation")
  protected void writeExpression(String name, XmlNamespace namespace,
      JRExpression expression, boolean writeClass,
      JRComponentElement componentElement, JRXmlWriter reportWriter)
      throws IOException {
    JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
    if (isNewerVersionOrEqual(componentElement, reportWriter,
        JRConstants.VERSION_4_1_1)) {
      writer.writeExpression(name, namespace, expression);
    } else {
      writer.writeExpression(name, namespace, expression, writeClass);
    }
  }
}
