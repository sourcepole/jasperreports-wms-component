package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * Property for WMS map service URL.
 */
public class WmsServiceUrlProperty extends StringProperty {
  
  private final StandardWmsMapComponent component;

  public WmsServiceUrlProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_WMS_SERVICE_URL);
    setDisplayName(I18n.getString("Global.Property.WmsServiceUrl"));
    setShortDescription(I18n.getString("Global.Property.WmsServiceUrl.desc"));
  }

  @Override
  public String getString() {
    return (this.component.getWmsServiceUrl() == null) ? 
            "" : this.component.getWmsServiceUrl();
  }

  @Override
  public String getOwnString() {
    return this.component.getWmsServiceUrl();
  }

  @Override
  public String getDefaultString() {
    return "http://";
  }

  @Override
  public void setString(String value) {
    this.component.setWmsServiceUrl(value);
  }

  @Override
  public void validate(Object value) {
    if(value != null) {
      try {
        new URL(value.toString());
      } catch(MalformedURLException e) {
        throw new IllegalArgumentException("A valid URL is expected");
      }
    }
  }

  @Override
  public boolean supportsDefaultValue() {
    return false;
  }

}
