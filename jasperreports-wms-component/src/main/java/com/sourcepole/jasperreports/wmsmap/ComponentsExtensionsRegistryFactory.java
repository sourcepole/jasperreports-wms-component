package com.sourcepole.jasperreports.wmsmap;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.component.ComponentManager;
import net.sf.jasperreports.engine.component.ComponentsBundle;
import net.sf.jasperreports.engine.component.DefaultComponentXmlParser;
import net.sf.jasperreports.engine.component.DefaultComponentsBundle;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;
import net.sf.jasperreports.extensions.SingletonExtensionRegistry;

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

  private static final SingletonExtensionRegistry<ComponentsBundle> REGISTRY;

  static {
    DefaultComponentsBundle bundle = new DefaultComponentsBundle();

    DefaultComponentXmlParser parser = new DefaultComponentXmlParser();
    parser.setNamespace(NAMESPACE);
    parser.setPublicSchemaLocation(XSD_LOCATION);
    parser.setInternalSchemaResource(XSD_RESOURCE);
    parser.setDigesterConfigurer(new WmsMapComponentsXmlDigesterConfigurer());
    bundle.setXmlParser(parser);

    WmsMapComponentManager wmsMapManager = new WmsMapComponentManager();
    wmsMapManager.setDesignConverter(WmsMapDesignConverter.getInstance());
    wmsMapManager.setComponentCompiler(new WmsMapCompiler());
    wmsMapManager.setComponentFillFactory(new WmsMapFillFactory());
    // wmsMapManager.setComponentXmlWriter(new WmsMapComponentsXmlWriter(null));

    HashMap<String, ComponentManager> componentManagers =
        new HashMap<String, ComponentManager>();
    componentManagers.put(WMS_MAP_COMPONENT_NAME, wmsMapManager);
    bundle.setComponentManagers(componentManagers);

    REGISTRY = new SingletonExtensionRegistry<ComponentsBundle>(
        ComponentsBundle.class, bundle);
  }

  @Override
  public ExtensionsRegistry createRegistry(String registryId,
      JRPropertiesMap properties) {
    return REGISTRY;
  }

}
