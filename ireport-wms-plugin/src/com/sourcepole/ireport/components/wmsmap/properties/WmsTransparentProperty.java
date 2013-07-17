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

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;

/**
 * Boolean property for WMS map transparent parameter.
 */
public class WmsTransparentProperty extends BooleanProperty {
  
  private final StandardWmsMapComponent component;

  public WmsTransparentProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_TRANSPARENT);
    setDisplayName(I18n.getString("Global.Property.WmsTransparent"));
    setShortDescription(I18n.getString("Global.Property.WmsTransparent.desc"));
  }

  @Override
  public Boolean getBoolean() {
    return component.getTransparent() == null ? 
            Boolean.FALSE : component.getTransparent();
  }

  @Override
  public Boolean getOwnBoolean() {
    return component.getTransparent();
  }

  @Override
  public Boolean getDefaultBoolean() {
    return Boolean.FALSE;
  }

  @Override
  public void setBoolean(Boolean bln) {
    Boolean transparent = bln == null ? Boolean.FALSE : bln;
    this.component.setTransparent(bln);
  }

}
