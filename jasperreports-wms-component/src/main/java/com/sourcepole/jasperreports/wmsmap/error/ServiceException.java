package com.sourcepole.jasperreports.wmsmap.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ServiceException {

  private String code;

  private String body;

  @XmlAttribute
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @XmlValue
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

}
