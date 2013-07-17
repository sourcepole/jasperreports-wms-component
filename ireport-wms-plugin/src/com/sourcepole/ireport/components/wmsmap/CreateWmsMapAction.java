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

import com.jaspersoft.ireport.designer.palette.actions.CreateReportElementAction;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * WMS map create action invoked when adding a new component to the report.
 */
public class CreateWmsMapAction extends CreateReportElementAction {

  @Override
  public JRDesignElement createReportElement(final JasperDesign jd) {
    JRDesignComponentElement component = new JRDesignComponentElement();
    StandardWmsMapComponent mapComponent = new StandardWmsMapComponent();
    mapComponent.setWmsServiceUrl("http://");
    mapComponent.setWmsVersion("1.1.1");
    mapComponent.setSrs("");
    mapComponent.setTransparent(Boolean.FALSE);
    mapComponent.setBBoxExpression(Misc.createExpression(null, "\"0.0,0.0,0.0,0.0\""));
    mapComponent.setLayersExpression(Misc.createExpression(null, "\"layer1,layer2\""));
    mapComponent.setStylesExpression(Misc.createExpression(null, "\"style1,style2\""));
    mapComponent.setUrlParametersExpression(Misc.createExpression(null, "\"\""));

    component.setComponent(mapComponent);
    ComponentKey key = new ComponentKey("http://sourcepole.com/jasperreports/components", "wmp", "wmsmap");
        component.setComponentKey(key);
    component.setWidth(200);
    component.setHeight(200);
    return component;
  }
}
