package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * Expression property for WMS map layer styles.
 */
public class WmsMapStylesProperty extends ExpressionProperty {

  private final StandardWmsMapComponent component;

  public WmsMapStylesProperty(StandardWmsMapComponent component, JRDesignDataset dataset) {
    super(component, dataset);
    this.component = component;
  }

  @Override
  public String getName() {
    return StandardWmsMapComponent.PROPERTY_STYLES_EXPRESSION;
  }

  @Override
  public String getDisplayName() {
    return I18n.getString("Global.Property.StylesExpression");
  }

  @Override
  public String getShortDescription() {
    return I18n.getString("Global.Property.StylesExpression.desc");
  }

  @Override
  public String getDefaultExpressionClassName() {
    return String.class.getName();
  }

  @Override
  public JRDesignExpression getExpression() {
    return (JRDesignExpression) component.getStylesExpression();
  }

  @Override
  public void setExpression(JRDesignExpression expression) {
    component.setStylesExpression(expression);
  }
}
