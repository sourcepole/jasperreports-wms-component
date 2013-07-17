package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * Expression property for WMS map BBOX.
 */
public class WmsMapBBoxProperty extends ExpressionProperty {

  private final StandardWmsMapComponent component;

  public WmsMapBBoxProperty(StandardWmsMapComponent component, JRDesignDataset dataset) {
    super(component, dataset);
    this.component = component;
  }

  @Override
  public String getName() {
    return StandardWmsMapComponent.PROPERTY_BBOX_EXPRESSION;
  }

  @Override
  public String getDisplayName() {
    return I18n.getString("Global.Property.BBoxExpression");
  }

  @Override
  public String getShortDescription() {
    return I18n.getString("Global.Property.BBoxExpression.desc");
  }

  @Override
  public String getDefaultExpressionClassName() {
    return String.class.getName();
  }

  @Override
  public JRDesignExpression getExpression() {
    return (JRDesignExpression) component.getBBoxExpression();
  }

  @Override
  public void setExpression(JRDesignExpression expression) {
    component.setBBoxExpression(expression);
  }
}
