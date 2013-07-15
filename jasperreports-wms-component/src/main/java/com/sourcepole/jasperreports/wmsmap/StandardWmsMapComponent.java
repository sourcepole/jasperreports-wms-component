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

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.util.JRCloneUtils;

public class StandardWmsMapComponent implements WmsMapComponent, Serializable,
    JRChangeEventsSupport {

  private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

  public static final String PROPERTY_BBOX_EXPRESSION = "bboxExpression";
  public static final String PROPERTY_LAYERS_EXPRESSION = "layersExpression";
  public static final String PROPERTY_STYLES_EXPRESSION = "stylesExpression";
  public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
  public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
  public static final String PROPERTY_WMS_SERVICE_URL = "wmsServiceUrl";
  public static final String PROPERTY_WMS_VERSION = "wmsVersion";
  public static final String PROPERTY_SRS = "srs";
  public static final String PROPERTY_TRANSPARENT = "transparent";
  public static final String PROPERTY_IMAGE_TYPE = "imageType";
  public static final String PROPERTY_URL_PARAMETERS_EXPRESSION =
      "urlParametersExpression";

  private JRExpression bboxExpression;
  private JRExpression layersExpression;
  private JRExpression stylesExpression;
  private JRExpression urlParametersExpression;
  private EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.NOW;
  private String evaluationGroup;
  private String wmsServiceUrl;
  private String wmsVersion;
  private String srs;
  private Boolean transparent = Boolean.FALSE;
  private String imageType;

  private transient JRPropertyChangeSupport eventSupport;

  public StandardWmsMapComponent() {
  }

  public StandardWmsMapComponent(WmsMapComponent map,
      JRBaseObjectFactory objectFactory) {
    this.bboxExpression = objectFactory.getExpression(map.getBBoxExpression());
    this.layersExpression = objectFactory.getExpression(map
        .getLayersExpression());
    this.stylesExpression = objectFactory.getExpression(map
        .getStylesExpression());
    this.evaluationTime = map.getEvaluationTime();
    this.evaluationGroup = map.getEvaluationGroup();
    this.wmsServiceUrl = map.getWmsServiceUrl();
    this.wmsVersion = map.getWmsVersion();
    this.srs = map.getSrs();
    this.imageType = map.getImageType();
    this.urlParametersExpression = map.getUrlParametersExpression();
  }

  public void setBBoxExpression(JRExpression bboxExpression) {
    Object old = this.bboxExpression;
    this.bboxExpression = bboxExpression;
    getEventSupport().firePropertyChange(PROPERTY_BBOX_EXPRESSION, old,
        this.bboxExpression);
  }

  @Override
  public JRExpression getBBoxExpression() {
    return bboxExpression;
  }

  public void setLayersExpression(JRExpression layersExpression) {
    Object old = this.layersExpression;
    this.layersExpression = layersExpression;
    getEventSupport().firePropertyChange(PROPERTY_LAYERS_EXPRESSION, old,
        this.layersExpression);
  }

  @Override
  public JRExpression getLayersExpression() {
    return layersExpression;
  }

  public void setStylesExpression(JRExpression stylesExpression) {
    Object old = this.stylesExpression;
    this.stylesExpression = stylesExpression;
    getEventSupport().firePropertyChange(PROPERTY_STYLES_EXPRESSION, old,
        this.stylesExpression);
  }

  @Override
  public JRExpression getStylesExpression() {
    return stylesExpression;
  }

  public void setUrlParametersExpression(JRExpression urlParametersExpression) {
    Object old = this.urlParametersExpression;
    this.urlParametersExpression = urlParametersExpression;
    getEventSupport().firePropertyChange(PROPERTY_URL_PARAMETERS_EXPRESSION,
        old, this.urlParametersExpression);
  }

  @Override
  public JRExpression getUrlParametersExpression() {
    return urlParametersExpression;
  }

  @Override
  public EvaluationTimeEnum getEvaluationTime() {
    return evaluationTime;
  }

  public void setEvaluationTime(EvaluationTimeEnum evaluationTimeValue) {
    Object old = this.evaluationTime;
    this.evaluationTime = evaluationTimeValue;
    getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, old,
        this.evaluationTime);
  }

  @Override
  public String getEvaluationGroup() {
    return evaluationGroup;
  }

  public void setEvaluationGroup(String evaluationGroup) {
    Object old = this.evaluationGroup;
    this.evaluationGroup = evaluationGroup;
    getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP,
        old, this.evaluationGroup);
  }

  @Override
  public String getImageType() {
    return imageType;
  }

  public void setImageType(String imageType) {
    Object old = this.imageType;
    this.imageType = imageType;
    getEventSupport().firePropertyChange(PROPERTY_IMAGE_TYPE, old,
        this.imageType);
  }

  @Override
  public String getWmsServiceUrl() {
    return wmsServiceUrl;
  }

  public void setWmsServiceUrl(String wmsServiceUrl) {
    Object old = this.wmsServiceUrl;
    this.wmsServiceUrl = wmsServiceUrl;
    getEventSupport().firePropertyChange(PROPERTY_WMS_SERVICE_URL, old,
        this.wmsServiceUrl);
  }

  @Override
  public String getWmsVersion() {
    return wmsVersion;
  }

  public void setWmsVersion(String wmsVersion) {
    Object old = this.wmsVersion;
    this.wmsVersion = wmsVersion;
    getEventSupport().firePropertyChange(PROPERTY_WMS_VERSION, old,
        this.wmsVersion);
  }

  @Override
  public String getSrs() {
    return srs;
  }

  public void setSrs(String srs) {
    Object old = this.srs;
    this.srs = srs;
    getEventSupport().firePropertyChange(PROPERTY_SRS, old, this.srs);
  }

  @Override
  public Boolean getTransparent() {
    return transparent;
  }

  public void setTransparent(Boolean transparent) {
    Object old = this.transparent;
    this.transparent = transparent;
    getEventSupport().firePropertyChange(PROPERTY_TRANSPARENT, old,
        this.transparent);
  }

  @Override
  public JRPropertyChangeSupport getEventSupport() {
    synchronized (this) {
      if (eventSupport == null) {
        eventSupport = new JRPropertyChangeSupport(this);
      }
    }
    return eventSupport;
  }

  @Override
  public Object clone() {
    StandardWmsMapComponent clone = null;
    try {
      clone = (StandardWmsMapComponent) super.clone();
    } catch (CloneNotSupportedException e) {
      // never
      throw new JRRuntimeException(e);
    }
    clone.bboxExpression = JRCloneUtils.nullSafeClone(bboxExpression);
    clone.layersExpression = JRCloneUtils.nullSafeClone(layersExpression);
    clone.stylesExpression = JRCloneUtils.nullSafeClone(stylesExpression);
    clone.urlParametersExpression = JRCloneUtils
            .nullSafeClone(urlParametersExpression);
    clone.eventSupport = null;
    return clone;
  }

}
