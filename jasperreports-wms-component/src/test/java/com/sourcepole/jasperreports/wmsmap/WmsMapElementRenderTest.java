package com.sourcepole.jasperreports.wmsmap;

import java.io.File;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests rendering of generic WMS map elements.
 */
@Ignore("Moved tests from generic element")
public class WmsMapElementRenderTest {

  private static final String TEST_REPORT_SOURCE = "src/test/resources/test.jrxml";
  private static final String TEST_REPORT_JASPER = "target/reports/test.jasper";

  private File outputDirectory;
  private JasperPrint reportPrint;

  @Before
  public void setUp() throws JRException {
    outputDirectory = new File("target/reports");
    outputDirectory.mkdirs();
    JasperCompileManager.compileReportToFile(TEST_REPORT_SOURCE,
        TEST_REPORT_JASPER);
    reportPrint = JasperFillManager.fillReport(
        TEST_REPORT_JASPER, null, new JREmptyDataSource());
  }

  @Test
  public void testRenderHtml() throws JRException {
    File html = new File(outputDirectory, "test.html");
    JasperExportManager.exportReportToHtmlFile(reportPrint,
        html.getAbsolutePath());
  }

  @Test
  public void testRenderPdf() throws JRException {
    File pdf = new File(outputDirectory, "test.pdf");
    JasperExportManager.exportReportToPdfFile(reportPrint,
        pdf.getAbsolutePath());
  }

}
