package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;

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
