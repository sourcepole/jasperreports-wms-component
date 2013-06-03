package com.sourcepole.jasperreports.wmsmap.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
    name = "ServiceExceptionReport",
    namespace = ServiceExceptionReport.QGIS_NS_URI)
public class ServiceExceptionReport {

  static final String QGIS_NS_URI = "http://www.opengis.net/ogc";

  private ServiceException serviceException;

  @XmlElement(name = "ServiceException", namespace = QGIS_NS_URI)
  public ServiceException getServiceException() {
    return serviceException;
  }

  public void setServiceException(ServiceException serviceException) {
    this.serviceException = serviceException;
  }

}
