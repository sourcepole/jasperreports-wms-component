/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.component.XmlDigesterConfigurer;
import net.sf.jasperreports.engine.xml.JRExpressionFactory;

import org.apache.commons.digester.Digester;

/**
 * XML digester for built-in component implementations.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ComponentsXmlHandler.java 5655 2012-09-12 13:25:21Z teodord $
 * @see ComponentsExtensionsRegistryFactory
 */
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
