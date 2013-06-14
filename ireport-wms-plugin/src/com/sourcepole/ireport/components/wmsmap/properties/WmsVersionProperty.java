package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class WmsVersionProperty extends StringProperty {
  
  private final StandardWmsMapComponent component;

  public WmsVersionProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_WMS_VERSION);
    setDisplayName(I18n.getString("Global.Property.WmsVersionExpression"));
    setShortDescription(I18n.getString("Global.Property.WmsVersionExpression.desc"));
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
    
  }

  @Override
  public boolean supportsDefaultValue() {
    return false;
  }

}
