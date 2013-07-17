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

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

/**
 * Interface for WMS Map component.
 */
public interface WmsMapComponent extends Component, JRCloneable {

  JRExpression getBBoxExpression();

  JRExpression getLayersExpression();

  JRExpression getStylesExpression();

  JRExpression getUrlParametersExpression();

  EvaluationTimeEnum getEvaluationTime();

  String getEvaluationGroup();

  String getWmsServiceUrl();

  String getWmsVersion();

  String getSrs();

  String getImageType();

  Boolean getTransparent();

}
