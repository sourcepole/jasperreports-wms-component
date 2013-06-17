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
