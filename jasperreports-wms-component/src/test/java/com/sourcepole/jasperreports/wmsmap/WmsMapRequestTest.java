package com.sourcepole.jasperreports.wmsmap;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link WmsMapRequest}.
 */
public class WmsMapRequestTest {

  private static final String BASE_URL = "http://wms.qgiscloud.com/olten/Solarkataster";
  private Map<String, Object> parameters;

  @Before
  public void setUp() {
    parameters = new LinkedHashMap<String, Object>();
    parameters.put(WmsMapRequest.Parameter.WMS_URL.name(), BASE_URL);
    parameters.put(WmsMapRequest.Parameter.LAYERS.name(), "Hintergrund");
    parameters.put(WmsMapRequest.Parameter.BBOX.name(),
        "634849.96085766,244281.95484911,635310.33560906,244655.89909163");
    parameters.put(WmsMapRequest.Parameter.SRS.name(), "EPSG:21781");
  }

  @Test
  public void testMapRequest() {
    WmsMapRequest request = WmsMapRequest.mapRequest("element", 100, 100,
        parameters);
    assertNotNull(request);
  }

  @Test(expected = IllegalStateException.class)
  public void testRequiredParameterUrl() {
    parameters.remove(WmsMapRequest.Parameter.WMS_URL.name());
    WmsMapRequest.mapRequest("elem", 100, 50, parameters);
  }

  @Test(expected = IllegalStateException.class)
  public void testRequiredParameterBBox() {
    parameters.remove(WmsMapRequest.Parameter.LAYERS.name());
    WmsMapRequest.mapRequest("elem", 100, 50, parameters);
  }

  @Test(expected = IllegalStateException.class)
  public void testRequiredParameterLayers() {
    parameters.remove(WmsMapRequest.Parameter.BBOX.name());
    WmsMapRequest.mapRequest("elem", 100, 50, parameters);
  }

  @Test(expected = IllegalStateException.class)
  public void testRequiredParameterSrs() {
    parameters.remove(WmsMapRequest.Parameter.SRS.name());
    WmsMapRequest.mapRequest("elem", 100, 50, parameters);
  }

  @Test(expected = IllegalStateException.class)
  public void testRequiredParameterCrs() {
    parameters.put(WmsMapRequest.Parameter.VERSION.name(), "1.3");
    WmsMapRequest.mapRequest("elem", 100, 50, parameters);
  }

  @Test
  public void testToMapUrl() throws MalformedURLException {
    WmsMapRequest request = WmsMapRequest.mapRequest("elem", 100, 50,
        parameters);
    URL mapUrl = request.toMapUrl();
    System.out.println(mapUrl);
  }

}
