package com.sourcepole.jasperreports.wmsmap;

enum WmsRequestType {
  CAPABILITIES("GetCapabilities"),
  GET_MAP("GetMap");

  private final String type;

  WmsRequestType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}