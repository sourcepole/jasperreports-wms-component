package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentCompiler;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

public class WmsMapCompiler implements ComponentCompiler {

  @Override
  public void collectExpressions(Component component,
      JRExpressionCollector collector) {
    WmsMapComponent map = (WmsMapComponent) component;
    collector.addExpression(map.getBBoxExpression());
    collector.addExpression(map.getLayersExpression());
    collector.addExpression(map.getStylesExpression());
  }

  @Override
  public Component toCompiledComponent(Component component,
      JRBaseObjectFactory baseFactory) {
    WmsMapComponent map = (WmsMapComponent) component;
    return new StandardWmsMapComponent(map, baseFactory);
  }

  @Override
  public void verify(Component component, JRVerifier verifier) {
    WmsMapComponent map = (WmsMapComponent) component;

    EvaluationTimeEnum evaluationTime = map.getEvaluationTime();
    if (evaluationTime == EvaluationTimeEnum.AUTO) {
      verifier.addBrokenRule(
          "Auto evaluation time is not supported for WMS maps", map);
    } else if (evaluationTime == EvaluationTimeEnum.GROUP) {
      String evaluationGroup = map.getEvaluationGroup();
      if (evaluationGroup == null || evaluationGroup.length() == 0) {
        verifier.addBrokenRule("No evaluation group set for map", map);
      } else if (!verifier.getReportDesign().getGroupsMap()
          .containsKey(evaluationGroup)) {
        verifier.addBrokenRule("Map evalution group \""
            + evaluationGroup + " not found", map);
      }
    }

    String wmsServiceUrl = map.getWmsServiceUrl();
    if (wmsServiceUrl == null || wmsServiceUrl.trim().isEmpty()) {
      verifier.addBrokenRule("WMS Service URL not set for map", map);
    }

    String srs = map.getSrs();
    if (srs == null || srs.trim().isEmpty()) {
      verifier.addBrokenRule("SRS/CRS not set for map", map);
    }

    verifyExpression(verifier, map, map.getBBoxExpression(), "BBox");
    verifyExpression(verifier, map, map.getLayersExpression(), "Layers");
  }

  private void verifyExpression(JRVerifier verifier, WmsMapComponent map,
      JRExpression jrExpression, String expressionName) {
    if (jrExpression == null || jrExpression.getText() == null
        || jrExpression.getText().trim().isEmpty()) {
      verifier.addBrokenRule(expressionName + " expression is empty", map);
    }
  }

}
