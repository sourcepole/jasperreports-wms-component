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

import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;

/**
 * Factory class used for {@link WmsMapComponentsXmlDigesterConfigurer} to parse
 * reports.
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
    String imageType =
        atts.getValue(WmsMapComponentsXmlWriter.ATTRIBUTE_IMAGE_TYPE);
    if (imageType != null) {
      map.setImageType(imageType);
    }

    return map;
  }
}
