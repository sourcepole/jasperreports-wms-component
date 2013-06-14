package com.sourcepole.jasperreports.wmsmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.component.ComponentManager;
import net.sf.jasperreports.engine.component.ComponentsBundle;
import net.sf.jasperreports.engine.component.DefaultComponentXmlParser;
import net.sf.jasperreports.engine.component.DefaultComponentsBundle;
import net.sf.jasperreports.engine.export.GenericElementHandler;
import net.sf.jasperreports.engine.export.GenericElementHandlerBundle;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

import com.sourcepole.jasperreports.wmsmap.fill.WmsMapFillFactory;

public class ComponentsExtensionsRegistryFactory implements
    ExtensionsRegistryFactory {

  public static final String NAMESPACE =
      "http://sourcepole.com/jasperreports/components";
  public static final String XSD_LOCATION =
      "http://sourcepole.com/xsd/components.xsd";
  public static final String XSD_RESOURCE =
      "com/sourcepole/jasperreports/wmsmap/components.xsd";

  public static final String WMS_MAP_COMPONENT_NAME = "wmsmap";

  private static final ExtensionsRegistry REGISTRY;

  private static final GenericElementHandlerBundle HANDLER_BUNDLE =
      new GenericElementHandlerBundle() {
        @Override
        public String getNamespace() {
          return ComponentsExtensionsRegistryFactory.NAMESPACE;
        }

        @Override
        public GenericElementHandler getHandler(String elementName,
            String exporterKey) {
          if (WmsMapPrintElement.WMS_MAP_ELEMENT_NAME.equals(elementName)) {
            if (JRGraphics2DExporter.GRAPHICS2D_EXPORTER_KEY
                .equals(exporterKey)) {
              return WmsMapElementGraphics2DHandler.getInstance();
            }
            if (JRHtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey)
                || JRXhtmlExporter.XHTML_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementHtmlHandler.getInstance();
            }
            else if (JRPdfExporter.PDF_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementPdfHandler.getInstance();
            }
            else if (JRXlsExporter.XLS_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementXlsHandler.getInstance();
            }
            else if (JExcelApiExporter.JXL_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementJExcelApiHandler.getInstance();
            }
            // else
            // if(JExcelApiMetadataExporter.JXL_METADATA_EXPORTER_KEY.equals(exporterKey))
            // {
            // return MapElementJExcelApiMetadataHandler.getInstance();
            // }
            else if (JRXlsxExporter.XLSX_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementXlsxHandler.getInstance();
            }
            else if (JRDocxExporter.DOCX_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementDocxHandler.getInstance();
            }
            else if (JRPptxExporter.PPTX_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementPptxHandler.getInstance();
            }
            else if (JRRtfExporter.RTF_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementRtfHandler.getInstance();
            }
            else if (JROdtExporter.ODT_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementOdtHandler.getInstance();
            }
            else if (JROdsExporter.ODS_EXPORTER_KEY.equals(exporterKey)) {
              return WmsMapElementOdsHandler.getInstance();
            }
          }
          return null;
        }
      };

  private static final DefaultComponentsBundle COMPONENTS_BUNDLE = new DefaultComponentsBundle();

  static {
    DefaultComponentXmlParser parser = new DefaultComponentXmlParser();
    parser.setNamespace(NAMESPACE);
    parser.setPublicSchemaLocation(XSD_LOCATION);
    parser.setInternalSchemaResource(XSD_RESOURCE);
    parser.setDigesterConfigurer(new WmsMapComponentsXmlDigesterConfigurer());
    COMPONENTS_BUNDLE.setXmlParser(parser);

    WmsMapComponentManager wmsMapManager = new WmsMapComponentManager();
    wmsMapManager.setDesignConverter(WmsMapDesignConverter.getInstance());
    wmsMapManager.setComponentCompiler(new WmsMapCompiler());
    wmsMapManager.setComponentFillFactory(new WmsMapFillFactory());
    // wmsMapManager.setComponentXmlWriter(new WmsMapComponentsXmlWriter(null));

    HashMap<String, ComponentManager> componentManagers =
        new HashMap<String, ComponentManager>();
    componentManagers.put(WMS_MAP_COMPONENT_NAME, wmsMapManager);
    COMPONENTS_BUNDLE.setComponentManagers(componentManagers);
  }

  static {
    REGISTRY = new ExtensionsRegistry() {

      @SuppressWarnings("unchecked")
      @Override
      public <T> List<T> getExtensions(Class<T> extensionType) {
        if (extensionType == ComponentsBundle.class) {
          return (List<T>) Collections.singletonList(COMPONENTS_BUNDLE);
        }
        if (extensionType == GenericElementHandlerBundle.class) {
          return (List<T>) Collections.singletonList(HANDLER_BUNDLE);
        }
        return null;
      }
    };
  }

  @Override
  public ExtensionsRegistry createRegistry(String registryId,
      JRPropertiesMap properties) {
    return REGISTRY;
  }

}
