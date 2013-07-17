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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;

import org.junit.Test;

public class WmsRequestBuilderTest {

  private static final String SERVICE_URL = "http://test.service.url";

  private WmsRequestBuilder request;

  @Test
  public void testCreateMapRequest() throws MalformedURLException {
    request = WmsRequestBuilder.createGetMapRequest(SERVICE_URL)
        .boundingBox("1.1,2.2,3.3,4.4")
        .layers("background,foreground")
        .styles("style,style")
        .srsCrs("EPGS:1234")
        .format("image/png")
        .transparent(true)
        .version("1.1.1")
        .height(100)
        .width(100).urlParameters("param=value");
    String url = request.toMapUrl().toExternalForm();
    assertNotNull(url);
    assertThat(url, startsWith("http://test.service.url?"));
    assertThat(url, containsString("BBOX=1.1%2C2.2%2C3.3%2C4.4"));
    assertThat(url, containsString("LAYERS=background%2Cforeground&"));
    assertThat(url, containsString("STYLE=style%2Cstyle&"));
    assertThat(url, containsString("FORMAT=image%2Fpng&"));
    assertThat(url, containsString("SRS=EPGS%3A1234&"));
    assertThat(url, containsString("VERSION=1.1.1&"));
    assertThat(url, containsString("TRANSPARENT=TRUE&"));
    assertThat(url, containsString("WIDTH=100&"));
    assertThat(url, containsString("HEIGHT=100&"));
    assertThat(url, endsWith("&param=value"));
  }

  @Test(expected = IllegalStateException.class)
  public void testLayersRequired() throws MalformedURLException {
    WmsRequestBuilder.createGetMapRequest(SERVICE_URL)
        .boundingBox("1.1")
        .toMapUrl();
  }

  @Test
  public void testStylesDefaults() throws MalformedURLException {
    String url = basicRequestBuilder()
        .toMapUrl().toExternalForm();
    assertThat(url, containsString("STYLE=%2C%2C"));
    System.out.println(url);
  }

  @Test
  public void testFormatDefault() throws MalformedURLException {
    String url = basicRequestBuilder().toMapUrl().toExternalForm();
    assertThat(url, containsString("FORMAT=image%2Fpng"));
  }

  @Test
  public void testTransparent() throws MalformedURLException {
    String url = basicRequestBuilder().toMapUrl().toExternalForm();
    assertThat(url, containsString("TRANSPARENT=FALSE"));
  }

  @Test
  public void testVersion() throws MalformedURLException {
    String url = basicRequestBuilder().toMapUrl().toExternalForm();
    assertThat(url, containsString("VERSION=1.1.1"));
  }

  @Test
  public void testCrs() throws MalformedURLException {
    String url = basicRequestBuilder().version("1.3").toMapUrl()
        .toExternalForm();
    assertThat(url, containsString("VERSION=1.3"));
    assertThat(url, containsString("CRS=EPSG%3A1234"));
    assertThat(url, not(containsString("SRS=EPSG%3A1234")));
  }

  private WmsRequestBuilder basicRequestBuilder() {
    return WmsRequestBuilder.createGetMapRequest(SERVICE_URL)
        .boundingBox("1.1")
        .layers("one,two,three")
        .srsCrs("EPSG:1234")
        .height(100).width(100);
  }

}
