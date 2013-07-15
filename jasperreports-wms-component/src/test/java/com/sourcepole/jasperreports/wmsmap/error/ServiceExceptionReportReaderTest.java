package com.sourcepole.jasperreports.wmsmap.error;

import static java.lang.Thread.currentThread;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class ServiceExceptionReportReaderTest {

  @Before
  public void setUp() {
  }

  @Test
  public void testUnmarshalNamespace() {
    InputStream is = currentThread().getContextClassLoader()
        .getResourceAsStream("serviceexception_ns.xml");
    ServiceExceptionReport report = ServiceExceptionReportReader.read(is);
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
    ServiceExceptionReport report = ServiceExceptionReportReader.read(is);
    assertNotNull(report);
    ServiceException serviceException = report.getServiceException();
    assertNotNull(serviceException);
    assertThat(serviceException.getCode(), is("OperationNotSupported"));
    assertThat(serviceException.getBody(), is("msWMSLoadGetMapParams(): "
        + "WMS server error. Unsupported SRS namespace "
        + "(only EPSG and AUTO currently supported)."));
  }

}
