package com.sourcepole.jasperreports.wmsmap;

/**
 * Enumeration of WMS {@link WmsRequestParameter#REQUEST} types.
 */
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