package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * Expression property for WMS map layers.
 */
public class WmsMapLayersProperty extends ExpressionProperty {

  private final StandardWmsMapComponent component;

  public WmsMapLayersProperty(StandardWmsMapComponent component, JRDesignDataset dataset) {
    super(component, dataset);
    this.component = component;
  }

  @Override
  public String getName() {
    return StandardWmsMapComponent.PROPERTY_LAYERS_EXPRESSION;
  }

  @Override
  public String getDisplayName() {
    return I18n.getString("Global.Property.LayersExpression");
  }

  @Override
  public String getShortDescription() {
    return I18n.getString("Global.Property.LayersExpression.desc");
  }

  @Override
  public String getDefaultExpressionClassName() {
    return String.class.getName();
  }

  @Override
  public JRDesignExpression getExpression() {
    return (JRDesignExpression) component.getLayersExpression();
  }

  @Override
  public void setExpression(JRDesignExpression expression) {
    component.setLayersExpression(expression);
  }
}
