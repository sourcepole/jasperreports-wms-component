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
package com.sourcepole.jasperreports.wmsmap.error;

import static java.lang.Thread.currentThread;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

public class ServiceExceptionReportTest {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  @Test
  public void testUnmarshalNamespace() {
    InputStream is = currentThread().getContextClassLoader()
        .getResourceAsStream("serviceexception_ns.xml");
    ServiceExceptionReport report = ServiceExceptionReport
        .parseExceptionReportFromStream(is, UTF_8);
    assertNotNull(report);
    ServiceException serviceException = report.getServiceException();
    assertNotNull(serviceException);
    assertThat(serviceException.getCode(), is("OperationNotSupported"));
    assertThat(serviceException.getBody(),
        is("Operation GetMapXXX not supported"));
  }

  @Test
  public void testUnmarshalDoctype() {
    InputStream is = currentThread().getContextClassLoader()
        .getResourceAsStream("serviceexception_doctype.xml");
    ServiceExceptionReport report = ServiceExceptionReport
        .parseExceptionReportFromStream(is, UTF_8);
    assertNotNull(report);
    ServiceException serviceException = report.getServiceException();
    assertNotNull(serviceException);
    assertThat(serviceException.getCode(), is("InvalidSRS"));
    assertThat(serviceException.getBody(), is("\nmsWMSLoadGetMapParams(): "
        + "WMS server error. Unsupported SRS namespace "
        + "(only EPSG and AUTO currently supported).\n"));
  }

  @Test
  public void testParseNonXmlExceptionReport() {
    String errorMessage = "Some unknown error occurred";
    byte[] bytes = errorMessage.getBytes(UTF_8);
    ByteArrayInputStream is = new ByteArrayInputStream(bytes);
    ServiceExceptionReport report = ServiceExceptionReport
        .parseExceptionReportFromStream(is, UTF_8);
    assertThat(report.getServiceException().getBody(), is(errorMessage));
  }

}
