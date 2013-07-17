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
package com.sourcepole.jasperreports.wmsmap.fill;

import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentFillFactory;
import net.sf.jasperreports.engine.component.FillComponent;
import net.sf.jasperreports.engine.fill.JRFillCloneFactory;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import com.sourcepole.jasperreports.wmsmap.WmsMapComponent;

/**
 * Factory for {@link WmsMapFillComponent}s.
 */
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
