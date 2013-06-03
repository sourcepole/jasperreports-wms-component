package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.component.ComponentXmlWriter;
import net.sf.jasperreports.engine.component.DefaultComponentManager;

public class WmsMapComponentManager extends DefaultComponentManager {

  @Override
  public ComponentXmlWriter getComponentXmlWriter(
      JasperReportsContext jasperReportsContext) {
    return new WmsMapComponentsXmlWriter(jasperReportsContext);
  }

}
