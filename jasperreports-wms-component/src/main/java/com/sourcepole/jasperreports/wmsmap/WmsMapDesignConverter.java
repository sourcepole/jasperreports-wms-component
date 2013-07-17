package com.sourcepole.jasperreports.wmsmap;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.component.ComponentDesignConverter;
import net.sf.jasperreports.engine.convert.ElementIconConverter;
import net.sf.jasperreports.engine.convert.ReportConverter;
import net.sf.jasperreports.engine.util.JRImageLoader;

/**
 * {@link ComponentDesignConverter} for WMS map component elements.
 */
public class WmsMapDesignConverter extends ElementIconConverter implements
    ComponentDesignConverter {

  private final static WmsMapDesignConverter INSTANCE = new WmsMapDesignConverter();

  private WmsMapDesignConverter() {
    super(JRImageLoader.SUBREPORT_IMAGE_RESOURCE);
  }

  public static WmsMapDesignConverter getInstance() {
    return INSTANCE;
  }

  @Override
  public JRPrintElement convert(ReportConverter reportConverter,
      JRComponentElement element) {
    return convert(reportConverter, (JRElement) element);
  }
}
