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
package com.sourcepole.ireport.components.wmsmap;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.ireport.components.wmsmap.properties.EvaluationGroupProperty;
import com.sourcepole.ireport.components.wmsmap.properties.EvaluationTimeProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsMapBBoxProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsMapImageTypeListProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsMapLayersProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsMapStylesProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsServiceUrlProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsSrsCrsProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsTransparentProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsUrlParametersProperty;
import com.sourcepole.ireport.components.wmsmap.properties.WmsVersionListProperty;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

/**
 * WMS map element node that creates the appropriate editor sheet to edit map
 * properties.
 */
public class WmsMapElementNode extends ElementNode {

  public WmsMapElementNode(JasperDesign jd, JRDesignElement element, Lookup doLkp) {
    super(jd, element, doLkp);
    setIconBaseWithExtension("com/sourcepole/ireport/components/wmsmap/wmsmap-16.png");
  }

  @Override
  public String getDisplayName() {
    return I18n.getString("WmsMapElementNode.name");
  }

  @Override
  protected org.openide.nodes.Sheet createSheet() {

    Sheet sheet = super.createSheet();

    // adding common properties...
    Sheet.Set propertySet = Sheet.createPropertiesSet();
    propertySet.setName("wmsmap");
    StandardWmsMapComponent component = (StandardWmsMapComponent) ((JRDesignComponentElement) getElement()).getComponent();
    propertySet.setDisplayName(I18n.getString("wmsmap"));

    JRDesignDataset dataset = ModelUtils.getElementDataset(getElement(), getJasperDesign());
    
    propertySet.put(new EvaluationTimeProperty(component, dataset));
    propertySet.put(new EvaluationGroupProperty(component, dataset));
    propertySet.put(new WmsServiceUrlProperty(component));
    propertySet.put(new WmsVersionListProperty(component));
    propertySet.put(new WmsSrsCrsProperty(component));
    propertySet.put(new WmsMapBBoxProperty(component, dataset));
    propertySet.put(new WmsMapLayersProperty(component, dataset));
    propertySet.put(new WmsMapStylesProperty(component, dataset));
    propertySet.put(new WmsMapImageTypeListProperty(component));
    propertySet.put(new WmsTransparentProperty(component));
    propertySet.put(new WmsUrlParametersProperty(component, dataset));

    sheet.put(propertySet);

    return sheet;
  }
}
