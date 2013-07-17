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
package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * Expression property for additional WMS map URL parameters.
 */
public class WmsUrlParametersProperty extends ExpressionProperty {

  private final StandardWmsMapComponent component;

  public WmsUrlParametersProperty(StandardWmsMapComponent component, JRDesignDataset dataset) {
    super(component, dataset);
    this.component = component;
  }

  @Override
  public String getName() {
    return StandardWmsMapComponent.PROPERTY_URL_PARAMETERS_EXPRESSION;
  }

  @Override
  public String getDisplayName() {
    return I18n.getString("Global.Property.UrlParameters");
  }

  @Override
  public String getShortDescription() {
    return I18n.getString("Global.Property.UrlParameters.desc");
  }

  @Override
  public String getDefaultExpressionClassName() {
    return String.class.getName();
  }

  @Override
  public JRDesignExpression getExpression() {
    return (JRDesignExpression) component.getUrlParametersExpression();
  }

  @Override
  public void setExpression(JRDesignExpression expression) {
    component.setUrlParametersExpression(expression);
  }
}
