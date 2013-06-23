package com.sourcepole.jasperreports.wmsmap;

import java.util.Locale;
import java.util.Map;

public enum WmsRequestParameter {

  /** Parameter for the WMS service base URL. */
  WMS_URL(true, null),

  /** Parameter {@code SERVICE}. */
  SERVICE(false, "WMS"),

  /** Parameter {@code SERVICE} type. */
  REQUEST(false, WmsRequestType.GET_MAP),

  /** Parameter {@code LAYERS}. */
  LAYERS(true, null),

  /** Parameter {@code STYLE}. */
  STYLE(false, null) {
    @Override
    Object defaultValue(Map<WmsRequestParameter, String> input) {
      String layers = input.get(LAYERS);
      if (layers == null) {
        return "";
      }
      return layers.replaceAll("[^,]", "");
    }
  },

  /** Parameter {@code FORMAT}. */
  FORMAT(false, "image/png"),

  /** Parameter {@code BBOX}. */
  BBOX(true, null),

  /** Parameter {@code VERSION}. */
  VERSION(false, new WmsVersion(1, 1, 1)) {
    @Override
    Object extract(Map<WmsRequestParameter, String> input) {
      if (!input.containsKey(this)) {
        return defaultValue(input);
      }
      String version = input.get(WmsRequestParameter.VERSION);
      return new WmsVersion(version);
    }
  },

  /**
   * Parameter {@code SRS} or {@code CRS}. SRS is used if version is &lt; 1.3;
   * CRS for version &gt;= 1.3.
   */
  SRS_CRS(true, null) {
    @Override
    String parameterName(Map<WmsRequestParameter, String> input) {
      WmsVersion version = (WmsVersion) WmsRequestParameter.VERSION
          .extract(input);
      if (version.compareTo(wmsVersion13) == -1) {
        return "SRS";
      }
      return "CRS";
    }
  },

  /** Parameter {@code WIDTH}. */
  WIDTH(true, null),

  /** Parameter {@code HEIGHT}. */
  HEIGHT(true, null),

  /** Parameter {@code TRANSPARENT}. */
  TRANSPARENT(false, Boolean.FALSE) {
    @Override
    Object extract(Map<WmsRequestParameter, String> input) {
      return super.extract(input).toString().toUpperCase(Locale.US);
    }
  },

  /** Extra URL parameters to append to a constructed URL. */
  URL_PARAMETERS(false, null);

  private static final WmsVersion wmsVersion13 = new WmsVersion(1, 3, 0);

  private boolean required = false;
  private final Object defaultValue;

  private WmsRequestParameter(boolean required, Object defaultValue) {
    this.required = required;
    this.defaultValue = defaultValue;
  }

  /**
   * Extracts an input parameter and returns it's value or a default value.
   */
  Object extract(Map<WmsRequestParameter, String> input) {
    Object value = input.get(this);
    if (value == null) {
      if (isRequired(input)) {
        throw new IllegalStateException(
            String.format("Missing required parameter: %s", this.name()));
      }
      return defaultValue(input);
    }
    return value;
  }

  /**
   * Returns the default value for the parameter.
   */
  Object defaultValue(Map<WmsRequestParameter, String> input) {
    return defaultValue;
  }

  /**
   * Returns the actual HTTP parameter name.
   */
  String parameterName(Map<WmsRequestParameter, String> input) {
    return this.name();
  }

  /** Returns if the parameter is required. */
  boolean isRequired(Map<WmsRequestParameter, String> input) {
    return this.required;
  }

  public static WmsRequestParameter[] parameterValues() {
    return new WmsRequestParameter[] {
        SERVICE,
        REQUEST,
        VERSION,
        BBOX,
        LAYERS,
        STYLE,
        SRS_CRS,
        FORMAT,
        HEIGHT,
        WIDTH,
        TRANSPARENT
    };
  }

}