/*
 * JasperReports/iReport WMS Component
 * 
 * Copyright (C) 2013 Sourcepole AG
 * 
 * JasperReports/iReport WMS Component is free software: you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * JasperReports/iReport WMS Component is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports/iReport WMS Component.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.jasperreports.wmsmap;

import java.util.Collections;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesMap;
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

public class ExtensionsRegistryFactory {

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

  private static final ExtensionsRegistry defaultExtensionsRegistry =
      new ExtensionsRegistry() {
        @Override
        @SuppressWarnings("unchecked")
        public <T> List<T> getExtensions(Class<T> extensionType) {
          if (GenericElementHandlerBundle.class.equals(extensionType)) {
            return (List<T>) Collections.singletonList((Object) HANDLER_BUNDLE);
          }
          return null;
        }
      };

  public ExtensionsRegistry createRegistry(String registryId,
      JRPropertiesMap properties) {
    return defaultExtensionsRegistry;
  }
}
