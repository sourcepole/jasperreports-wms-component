package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.component.XmlDigesterConfigurer;
import net.sf.jasperreports.engine.xml.JRExpressionFactory;

import org.apache.commons.digester.Digester;

public class WmsMapComponentsXmlDigesterConfigurer implements
    XmlDigesterConfigurer
{
  @Override
  public void configureDigester(Digester digester) {
    addMapRules(digester);
  }

  protected void addMapRules(Digester digester) {
    String mapPattern = "*/componentElement/wmsmap";
    digester.addFactoryCreate(mapPattern, WmsMapXmlFactory.class);

    String bboxExpressionPattern = mapPattern + "/bboxExpression";
    digester.addFactoryCreate(bboxExpressionPattern,
        JRExpressionFactory.class.getName());
    digester.addCallMethod(bboxExpressionPattern, "setText", 0);
    digester.addSetNext(bboxExpressionPattern, "setBBoxExpression",
        JRExpression.class.getName());

    String layersExpressionPattern = mapPattern + "/layersExpression";
    digester.addFactoryCreate(layersExpressionPattern,
        JRExpressionFactory.class.getName());
    digester.addCallMethod(layersExpressionPattern, "setText", 0);
    digester.addSetNext(layersExpressionPattern, "setLayersExpression",
        JRExpression.class.getName());

    String stylesExpressionPattern = mapPattern + "/stylesExpression";
    digester.addFactoryCreate(stylesExpressionPattern,
        JRExpressionFactory.class.getName());
    digester.addCallMethod(stylesExpressionPattern, "setText", 0);
    digester.addSetNext(stylesExpressionPattern, "setStylesExpression",
        JRExpression.class.getName());

    String componentNamespace = digester.getRuleNamespaceURI();

    // leave the digester namespace in the same state
    digester.setRuleNamespaceURI(componentNamespace);
  }

}
