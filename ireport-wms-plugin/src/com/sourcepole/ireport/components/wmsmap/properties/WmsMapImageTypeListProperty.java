package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.EnumProperty;
import com.jaspersoft.ireport.designer.sheet.properties.StringListProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.type.WmsMapImageTypeEnum;
import java.util.Arrays;
import java.util.List;

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
    return 
  }

  @Override
  public String getOwnString() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public String getDefaultString() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setString(String string) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
}
