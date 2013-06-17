package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

public interface WmsMapComponent extends Component, JRCloneable {

  JRExpression getBBoxExpression();

  JRExpression getLayersExpression();

  JRExpression getStylesExpression();

  EvaluationTimeEnum getEvaluationTime();

  String getEvaluationGroup();

  String getWmsServiceUrl();

  String getWmsVersion();

  String getSrs();

  String getImageType();

  Boolean getTransparent();
}
