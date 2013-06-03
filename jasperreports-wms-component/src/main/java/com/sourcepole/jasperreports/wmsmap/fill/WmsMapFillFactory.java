package com.sourcepole.jasperreports.wmsmap.fill;

import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentFillFactory;
import net.sf.jasperreports.engine.component.FillComponent;
import net.sf.jasperreports.engine.fill.JRFillCloneFactory;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import com.sourcepole.jasperreports.wmsmap.WmsMapComponent;

public class WmsMapFillFactory implements ComponentFillFactory {

  @Override
  public FillComponent toFillComponent(Component component,
      JRFillObjectFactory factory) {
    WmsMapComponent map = (WmsMapComponent) component;
    return new WmsMapFillComponent(map, factory);
  }

  @Override
  public FillComponent cloneFillComponent(FillComponent component,
      JRFillCloneFactory factory) {
    WmsMapFillComponent fillMap = (WmsMapFillComponent) component;
    return new WmsMapFillComponent(fillMap.getMap());
  }

}
