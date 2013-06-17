package com.sourcepole.jasperreports.wmsmap;

import static java.lang.String.format;
import static java.net.URLEncoder.encode;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * WMS Map request wraps all parameters to create a map URL.
 */
public class WmsMapRequest {

  private static final String UTF_8 = "UTF-8";

  public static enum Parameter {

    /** Parameter for the WMS service base URL. */
    WMS_URL(true, null),

    /** Parameter {@code SERVICE}. */
    SERVICE(false, "WMS"),

    /** Parameter {@code SERVICE} type. */
    REQUEST(false, "GetMap"),

    /** Parameter {@code LAYERS}. */
    LAYERS(true, null),

    /** Parameter {@code STYLE}. */
    STYLE(false, null) {
      @Override
      Object defaultValue(Map<String, Object> input,
          Map<Parameter, Object> target) {
        String layers = (String) target.get(LAYERS);
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
      Object extract(Map<String, Object> input, Map<Parameter, Object> target) {
        if (!input.containsKey(this.name())) {
          return defaultValue(input, target);
        }
        String version = input.get(this.name()).toString();
        return new WmsVersion(version);
      }
    },

    /** Parameter {@code SRS} (WMS version &lt; 1.3). */
    SRS(true, null) {
      @Override
      boolean isRequired(Map<String, Object> input,
          Map<Parameter, Object> target) {
        WmsVersion version = (WmsVersion) target.get(VERSION);
        return version.compareTo(wmsVersion13) < 0;
      }
    },

    /** Parameter {@code CRS} (WMS version &gt;= 1.3). */
    CRS(true, null) {
      @Override
      boolean isRequired(Map<String, Object> input,
          Map<Parameter, Object> target) {
        WmsVersion version = (WmsVersion) target.get(VERSION);
        return version.compareTo(wmsVersion13) >= 0;
      }
    },

    /** Parameter {@code WIDTH}. */
    WIDTH(true, null),

    /** Parameter {@code HEIGHT}. */
    HEIGHT(true, null),

    /** Parameter {@code TRANSPARENT}. */
    TRANSPARENT(false, Boolean.FALSE),

    /** Extra URL parameters to append to a constructed URL. */
    URL_PARAMETERS(false, null);

    private static final WmsVersion wmsVersion13 = new WmsVersion(1, 3, 0);

    private boolean required = false;
    private final Object defaultValue;

    private Parameter(boolean required, Object defaultValue) {
      this.required = required;
      this.defaultValue = defaultValue;
    }

    /**
     * Extracts an input parameter and returns it's value or a default value.
     */
    Object extract(Map<String, Object> input, Map<Parameter, Object> target) {
      Object value = input.remove(this.name());
      if (value == null) {
        if (isRequired(input, target)) {
          throw new IllegalStateException(
              format("Missing required parameter: %s", this.name()));
        }
        return defaultValue(input, target);
      }
      return value;
    }

    /**
     * Returns the default value for the parameter.
     */
    Object defaultValue(Map<String, Object> input, Map<Parameter, Object> target) {
      return defaultValue;
    }

    void apply(Map<String, Object> input, Map<Parameter, Object> target) {
      Object value = extract(input, target);
      if (value != null) {
        target.put(this, value);
      }
    }

    /** Returns if the parameter is required. */
    boolean isRequired(Map<String, Object> input, Map<Parameter, Object> target) {
      return this.required;
    }
  }

  private final String elementName;

  private final int width;

  private final int height;

  private final Map<Parameter, Object> parameters =
      new LinkedHashMap<Parameter, Object>();

  /** Additional parameters. */
  private final Map<String, Object> extraParameters =
      new LinkedHashMap<String, Object>();

  private WmsMapRequest(String elementName, int width, int height,
      Map<String, Object> inputParams) {
    this.elementName = elementName;
    this.width = width;
    this.height = height;
    Map<String, Object> input = new LinkedHashMap<String, Object>(inputParams);
    input.put(Parameter.WIDTH.name(), width);
    input.put(Parameter.HEIGHT.name(), height);
    for (Parameter parameter : Parameter.values()) {
      parameter.apply(input, parameters);
    }
  }

  /**
   * Returns a new map request.
   * 
   * @param name Name of the element
   * @param w Map element width
   * @param h Map element height
   * @param params Map parameters
   * @return A new WmsMapRequest
   */
  public static WmsMapRequest mapRequest(String name,
      int w, int h, Map<String, Object> params) {
    return new WmsMapRequest(name, w, h, params);
  }

  public String getElementName() {
    return elementName;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public URL toMapUrl() throws MalformedURLException {
    StringBuilder url = new StringBuilder();
    Map<Parameter, Object> params =
        new LinkedHashMap<Parameter, Object>(this.parameters);
    String wmsUrl = params.remove(Parameter.WMS_URL).toString();
    url.append(wmsUrl);
    if (wmsUrl.indexOf("?") == -1) {
      url.append("?");
    }
    java.util.List<String> paramList = new ArrayList<String>();
    for (Entry<Parameter, Object> param : params.entrySet()) {
      paramList.add(encodeParameter(param.getKey().name(), param.getValue()));
    }
    for (Entry<String, Object> param : this.extraParameters.entrySet()) {
      paramList.add(encodeParameter(param.getKey(), param.getValue()));
    }
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      String param = iterator.next();
      url.append(param);
      if (iterator.hasNext()) {
        url.append("&");
      }
    }
    return new URL(url.toString());
  }

  private static String encodeParameter(String key, Object value) {
    try {
      String encName = encode(key, UTF_8);
      String encValue = encode(value.toString(), UTF_8);
      String encodedParameter = format("%s=%s", encName, encValue);
      return encodedParameter;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Encoding UTF-8 not supported on runtime");
    }
  }
}
