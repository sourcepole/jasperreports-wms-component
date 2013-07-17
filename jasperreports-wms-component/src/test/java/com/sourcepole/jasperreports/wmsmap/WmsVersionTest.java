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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link WmsVersion}.
 */
public class WmsVersionTest {

  @Test
  public void testWmsVersionString() {
    WmsVersion version = new WmsVersion("1.2.3");
    assertThat(version.getMajor(), is(1));
    assertThat(version.getMinor(), is(2));
    assertThat(version.getBuild(), is(3));
  }

  @Test
  public void testWmsVersionStringPart() {
    WmsVersion version = new WmsVersion("1.2");
    assertThat(version.getMajor(), is(1));
    assertThat(version.getMinor(), is(2));
    assertThat(version.getBuild(), is(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWmsVersionStringInvalid() {
    new WmsVersion("1.2a.3");
  }

  @Test
  public void testWmsVersionIntIntInt() {
    WmsVersion version = new WmsVersion(1, 2, 3);
    assertThat(version.getMajor(), is(1));
    assertThat(version.getMinor(), is(2));
    assertThat(version.getBuild(), is(3));
  }

  @Test
  public void testCompareTo() {
    WmsVersion version111 = new WmsVersion("1.1.1");
    WmsVersion version112 = new WmsVersion("1.1.2");
    WmsVersion version110 = new WmsVersion("1.1");
    WmsVersion version130 = new WmsVersion("1.3");
    WmsVersion version200 = new WmsVersion("2.0");
    assertThat(version111.compareTo(version112), is(-1));
    assertThat(version112.compareTo(version111), is(1));
    assertThat(version111.compareTo(version130), is(-1));
    assertThat(version130.compareTo(version111), is(1));
    assertThat(version130.compareTo(version130), is(0));
    assertThat(version110.compareTo(version111), is(-1));
    assertThat(version200.compareTo(version111), is(1));
    assertThat(version130.compareTo(version200), is(-1));
  }

  @Test(expected = NullPointerException.class)
  public void testCompareToNull() {
    WmsVersion version = new WmsVersion("1.1.1");
    version.compareTo(null);
  }

  @Test
  public void testToString() {
    WmsVersion version = new WmsVersion("1.3");
    assertThat(version.toString(), is("1.3.0"));
  }

  @Test
  public void testToVersionString() {
    WmsVersion version1 = new WmsVersion(1, 0, 0);
    assertThat(version1.toVersionString(), is("1"));
    WmsVersion version13 = new WmsVersion(1, 3, 0);
    assertThat(version13.toVersionString(), is("1.3"));
    WmsVersion version111 = new WmsVersion(1, 1, 1);
    assertThat(version111.toVersionString(), is("1.1.1"));
  }

}
