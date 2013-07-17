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

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.StringListProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.WmsVersion;
import java.util.Arrays;
import java.util.List;

/**
 * Property for WMS map service version.
 */
public class WmsVersionListProperty extends StringListProperty {

  private final StandardWmsMapComponent component;

  public WmsVersionListProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_WMS_VERSION);
    setDisplayName(I18n.getString("Global.Property.WmsVersion"));
    setShortDescription(I18n.getString("Global.Property.WmsVersion.desc"));
  }

  @Override
  public String getString() {
    return (this.component.getWmsVersion() == null) ? 
            "" : this.component.getWmsVersion();
  }

  @Override
  public String getOwnString() {
    return this.component.getWmsVersion();
  }

  @Override
  public String getDefaultString() {
    return "1.1.1";
  }

  @Override
  public void setString(String value) {
    this.component.setWmsVersion(value);
  }

  @Override
  public void validate(Object value) {
    if(value == null) {
      throw new IllegalArgumentException("Version must not be empty");
    }
    WmsVersion.validateVersion(value.toString());
  }

  @Override
  public List getTagList() {
    Tag version111 = new Tag("1.1.1");
    Tag version13 = new Tag("1.3");
    return Arrays.asList(version111, version13);
  }

  @Override
  public boolean supportsDefaultValue() {
    return true;
  }
}
