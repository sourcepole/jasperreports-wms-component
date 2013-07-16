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
