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

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;

/**
 * Property for WMS map SRS/CRS parameter.
 */
public class WmsSrsCrsProperty extends StringProperty {
  
  private final StandardWmsMapComponent component;

  public WmsSrsCrsProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_SRS);
    setDisplayName(I18n.getString("Global.Property.WmsSrsCrs"));
    setShortDescription(I18n.getString("Global.Property.WmsSrsCrs.desc"));
  }

  @Override
  public String getString() {
    return (this.component.getSrs() == null) ? 
            "" : this.component.getSrs();
  }

  @Override
  public String getOwnString() {
    return this.component.getSrs();
  }

  @Override
  public String getDefaultString() {
    return "";
  }

  @Override
  public void setString(String value) {
    this.component.setSrs(value);
  }

  @Override
  public void validate(Object value) {
    if(value == null || value.toString().trim().length() == 0){
      throw new IllegalArgumentException("SRS/CRS is required");
    }
  }

  @Override
  public boolean supportsDefaultValue() {
    return false;
  }

}
