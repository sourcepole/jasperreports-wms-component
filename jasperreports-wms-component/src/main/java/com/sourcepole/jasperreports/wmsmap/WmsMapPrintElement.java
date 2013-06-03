package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRGenericElementType;

import com.sourcepole.jasperreports.wmsmap.fill.WmsMapFillComponent;

/**
 * Utility class with constants used by {@link WmsMapFillComponent}.
 */
public final class WmsMapPrintElement
{
  /**
   * The name of WMS map generic elements.
   */
  public static final String WMS_MAP_ELEMENT_NAME = "wmsmap";

  /**
   * The qualified type of WMS map generic elements.
   */
  public static final JRGenericElementType WMS_MAP_ELEMENT_TYPE =
      new JRGenericElementType(
          ComponentsExtensionsRegistryFactory.NAMESPACE,
          WMS_MAP_ELEMENT_NAME);

  /** Parameter {@code BBox}. */
  public static final String PARAMETER_BBOX = "bbox";

  /** Parameter {@code layers}. */
  public static final String PARAMETER_LAYERS = "layers";

  /** Parameter {@code styles}. */
  public static final String PARAMETER_STYLES = "styles";

  /** Parameter {@code imageType}. */
  public static final String PARAMETER_IMAGE_TYPE = "imageType";

  /** Parameter {@code cacheRenderer}. */
  public static final String PARAMETER_CACHE_RENDERER = "cacheRenderer";

  public static final String DEFAULT_BBOX = "";

  private WmsMapPrintElement()
  {
  }
}
