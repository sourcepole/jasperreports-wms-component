package com.sourcepole.ireport.components.wmsmap;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.ElementNodeFactory;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Lookup;

public class WmsMapElementNodeFactory implements ElementNodeFactory {

  @Override
  public ElementNode createElementNode(JasperDesign jd, JRDesignComponentElement element, Lookup lkp) {
    return new WmsMapElementNode(jd, element, lkp);
  }

  @Override
  public JRDesignElementWidget createElementWidget(AbstractReportObjectScene scene, JRDesignElement element) {
    return new WmsMapComponentWidget(scene, element);

  }
}
