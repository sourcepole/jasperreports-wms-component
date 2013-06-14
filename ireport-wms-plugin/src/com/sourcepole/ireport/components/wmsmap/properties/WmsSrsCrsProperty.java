package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class WmsSrsCrsProperty extends StringProperty {
  
  private final StandardWmsMapComponent component;

  public WmsSrsCrsProperty(StandardWmsMapComponent component) {
    super(component);
    this.component = component;
    setName(StandardWmsMapComponent.PROPERTY_WMS_VERSION);
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
