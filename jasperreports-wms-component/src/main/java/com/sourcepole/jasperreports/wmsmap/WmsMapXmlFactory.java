package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;

import com.sourcepole.jasperreports.wmsmap.type.WmsMapImageTypeEnum;

/**
 * 
 */
public class WmsMapXmlFactory extends JRBaseFactory {

  public static final String ATTRIBUTE_imageType = "imageType";

  @Override
  public Object createObject(Attributes atts) {
    StandardWmsMapComponent map = new StandardWmsMapComponent();

    EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.getByName(atts
        .getValue(JRXmlConstants.ATTRIBUTE_evaluationTime));
    if (evaluationTime != null) {
      map.setEvaluationTime(evaluationTime);
    }

    if (map.getEvaluationTime() == EvaluationTimeEnum.GROUP) {
      String groupName = atts
          .getValue(JRXmlConstants.ATTRIBUTE_evaluationGroup);
      map.setEvaluationGroup(groupName);
    }

    // Service URL attribute
    String wmsServiceUrl =
        atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_WMS_SERVICE_URL);
    if (wmsServiceUrl != null) {
      map.setWmsServiceUrl(wmsServiceUrl);
    }

    // Service Version
    String wmsVersion =
        atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_WMS_VERSION);
    if (wmsVersion != null) {
      map.setWmsVersion(wmsVersion);
    }

    // SRS / CRS
    String crs =
        atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_SRS);
    if (crs != null) {
      map.setSrs(crs);
    }

    // Transparency
    String transparent =
        atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_TRANSPARENT);
    if (transparent != null) {
      map.setTransparent(Boolean.valueOf(transparent));
    }

    // Image type
    WmsMapImageTypeEnum imageType =
        WmsMapImageTypeEnum.getByName(
            atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_IMAGE_TYPE));
    if (imageType != null) {
      map.setImageType(imageType);
    }

    return map;
  }
}
