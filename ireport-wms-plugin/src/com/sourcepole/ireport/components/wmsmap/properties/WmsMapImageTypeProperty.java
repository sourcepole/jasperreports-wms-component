package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.EnumProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import com.sourcepole.jasperreports.wmsmap.type.WmsMapImageTypeEnum;
import java.util.List;

public class WmsMapImageTypeProperty extends EnumProperty {

  StandardWmsMapComponent mapComponent = null;

  public WmsMapImageTypeProperty(StandardWmsMapComponent mapComponent) {
    super(WmsMapImageTypeEnum.class, mapComponent);
    this.mapComponent = mapComponent;

    this.setName(StandardWmsMapComponent.PROPERTY_IMAGE_TYPE);
    this.setDisplayName(I18n.getString("Global.Property.WmsMapImageType"));
    this.setShortDescription(I18n.getString("Global.Property.WmsMapImageType.desc"));
  }

  @Override
  public List getTagList() {
    List tags = new java.util.ArrayList();
    tags.add(new Tag(WmsMapImageTypeEnum.PNG, I18n.getString("WmsMapImageTypeProperty.enum.png")));
    tags.add(new Tag(WmsMapImageTypeEnum.PNG_8, I18n.getString("WmsMapImageTypeProperty.enum.png8")));
    tags.add(new Tag(WmsMapImageTypeEnum.PNG_32, I18n.getString("WmsMapImageTypeProperty.enum.png32")));
    tags.add(new Tag(WmsMapImageTypeEnum.JPG, I18n.getString("WmsMapImageTypeProperty.enum.jpg")));
    return tags;
  }

  @Override
  public Object getPropertyValue() {
    return mapComponent.getImageType() == null ? getDefaultValue() : mapComponent.getImageType();
  }

  @Override
  public Object getOwnPropertyValue() {
    return getPropertyValue();
  }

  @Override
  public Object getDefaultValue() {
    return WmsMapImageTypeEnum.PNG;
  }

  @Override
  public void setPropertyValue(Object value) {
    mapComponent.setImageType((WmsMapImageTypeEnum) value);
  }
}
