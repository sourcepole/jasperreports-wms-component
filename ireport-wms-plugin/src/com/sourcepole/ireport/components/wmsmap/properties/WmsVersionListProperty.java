package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.StringListProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.WmsVersion;
import java.util.Arrays;
import java.util.List;

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
