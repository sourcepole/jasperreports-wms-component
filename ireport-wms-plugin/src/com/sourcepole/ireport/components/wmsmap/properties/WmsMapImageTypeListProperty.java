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
import java.util.Arrays;
import java.util.List;

/**
 * Property for WMS map image type.
 */
public class WmsMapImageTypeListProperty extends StringListProperty {

  StandardWmsMapComponent component = null;

  public WmsMapImageTypeListProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;

    this.setName(StandardWmsMapComponent.PROPERTY_IMAGE_TYPE);
    this.setDisplayName(I18n.getString("Global.Property.WmsMapImageType"));
    this.setShortDescription(I18n.getString("Global.Property.WmsMapImageType.desc"));
  }

  @Override
  public List getTagList() {
    Tag imagePng = new Tag("image/png");
    Tag imageJpeg = new Tag("image/jpeg");
    return Arrays.asList(imagePng, imageJpeg);
  }

  @Override
  public String getString() {
    return component.getImageType() == null ? 
            getDefaultString() : component.getImageType();
  }

  @Override
  public String getOwnString() {
    return component.getImageType();
  }

  @Override
  public String getDefaultString() {
    return "image/png";
  }

  @Override
  public void setString(String imageType) {
    this.component.setImageType(imageType);
  }
  
}
