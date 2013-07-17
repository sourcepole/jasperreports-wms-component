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
package com.sourcepole.jasperreports.wmsmap;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.awt.Graphics2D;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterContext;
import net.sf.jasperreports.engine.export.draw.Offset;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link WmsMapElementGraphics2DHandler}.
 */
public class WmsMapElementGraphics2DHandlerTest {

  private WmsMapElementGraphics2DHandler handler;

  @Before
  public void setUp() {
    handler = new WmsMapElementGraphics2DHandler();
  }

  @Ignore
  @Test
  public void testExportElement() throws JRException {
    JRGraphics2DExporter exporter = new JRGraphics2DExporter();
    JRGraphics2DExporterContext context = mock(JRGraphics2DExporterContext.class);
    when(context.getExporter()).thenReturn(exporter);
    JRGenericPrintElement element = mock(JRGenericPrintElement.class);
    java.awt.Container container = new Container();
    Graphics2D graphics = (Graphics2D) container.getGraphics();
    Offset offset = new Offset(0, 0);
    handler.exportElement(context, element, graphics, offset);
  }

  @Test
  public void testToExport() {
    assertTrue(handler.toExport(mock(JRGenericPrintElement.class)));
  }

}
