package com.sourcepole.jasperreports.wmsmap.error;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@XmlRootElement(name = "ServiceExceptionReport")
public class ServiceExceptionReport {

  private ServiceException serviceException;

  @XmlElement(name = "ServiceException")
  public ServiceException getServiceException() {
    return serviceException;
  }

  public void setServiceException(ServiceException serviceException) {
    this.serviceException = serviceException;
  }

  /**
   * Parses a {@link ServiceExceptionReport} from the specified {@code stream}.
   * If the given {@code stream} does not contain a valid
   * {@link ServiceExceptionReport} then a report is created that contains the
   * contents of the stream in the body element.
   * 
   * @param inputStream {@link InputStream} to parse the
   *          {@link ServiceExceptionReport} from
   * @param encoding Encoding to use
   * @return The parsed {@link ServiceExceptionReport}
   */
  public static ServiceExceptionReport parseExceptionReportFromStream(
      InputStream inputStream, Charset encoding) {
    try {
      byte[] response = toByteArray(inputStream);
      try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(response));
        return JAXB
            .unmarshal(new DOMSource(document), ServiceExceptionReport.class);
      } catch (SAXException e) {
        ServiceExceptionReport report = new ServiceExceptionReport();
        ServiceException exception = new ServiceException();
        exception.setBody(new String(response, encoding));
        report.setServiceException(exception);
        return report;
      } catch (ParserConfigurationException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException("Unable to read server response", e);
    }
  }

  private static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while ((n = input.read(buffer)) > -1) {
      output.write(buffer, 0, n);
    }
    return output.toByteArray();
  }

  public boolean isParsed() {
    return true;
  }

}
