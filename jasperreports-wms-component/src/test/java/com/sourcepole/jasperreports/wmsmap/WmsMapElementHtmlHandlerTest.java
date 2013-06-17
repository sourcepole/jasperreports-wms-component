package com.sourcepole.jasperreports.wmsmap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.export.JRHtmlExporterContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link WmsMapElementHtmlHandler}.
 */
public class WmsMapElementHtmlHandlerTest {

  private WmsMapElementHtmlHandler handler;
  private static final String BASE_URL = "http://wms.qgiscloud.com/test";
  private Map<String, Object> parameters;

  @Before
  public void setUp() {
    parameters = new LinkedHashMap<String, Object>();
    parameters.put(WmsMapRequest.Parameter.WMS_URL.name(), BASE_URL);
    parameters.put(WmsMapRequest.Parameter.LAYERS.name(), "Hintergrund");
    parameters.put(WmsMapRequest.Parameter.BBOX.name(),
        "634849.96085766,244281.95484911,635310.33560906,244655.89909163");
    parameters.put(WmsMapRequest.Parameter.SRS.name(), "EPSG:21781");
    handler = new WmsMapElementHtmlHandler();
  }

  @Test
  public void testToExport() {
    assertTrue(handler.toExport(null));
  }

  @Ignore("Moved test from generic element")
  @Test
  public void testGetHtmlFragment() {
    JRGenericPrintElement element = mock(JRGenericPrintElement.class);
    when(element.getParameterNames()).thenReturn(parameters.keySet());
    when(element.getParameterValue(WmsMapRequest.Parameter.WMS_URL.name()))
        .thenReturn(BASE_URL);
    when(element.getParameterValue(WmsMapRequest.Parameter.LAYERS.name()))
        .thenReturn("Hintergrund");
    when(element.getParameterValue(WmsMapRequest.Parameter.BBOX.name()))
        .thenReturn("1,1,1,1");
    when(element.getParameterValue(WmsMapRequest.Parameter.SRS.name()))
        .thenReturn("EPSG:21781");
    when(element.getWidth()).thenReturn(100);
    when(element.getHeight()).thenReturn(100);
    String htmlFragment = handler.getHtmlFragment(
        mock(JRHtmlExporterContext.class), element);
    assertThat(htmlFragment, startsWith("<img "));
    assertThat(htmlFragment, containsString(BASE_URL));
    assertThat(htmlFragment, containsString("WIDTH=100"));
    assertThat(htmlFragment, containsString("HEIGHT=100"));
  }

}
