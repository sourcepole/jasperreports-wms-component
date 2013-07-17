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

import static java.lang.String.format;

/**
 * Encapsulates a version.
 */
public class WmsVersion implements Comparable<WmsVersion> {

  private final int major;
  private final int minor;
  private final int build;

  public WmsVersion(String version) {
    String[] versionParts = version.split("\\.");
    major = versionElement(versionParts, 0);
    minor = versionElement(versionParts, 1);
    build = versionElement(versionParts, 2);
  }

  public WmsVersion(int major, int minor, int build) {
    this.major = major;
    this.minor = minor;
    this.build = build;
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getBuild() {
    return build;
  }

  @Override
  public int compareTo(WmsVersion o) {
    if (o == null) {
      throw new NullPointerException("Version to compare to must not be null");
    }
    if (this.major != o.major) {
      return this.major < o.major ? -1 : 1;
    }
    if (this.minor != o.minor) {
      return this.minor < o.minor ? -1 : 1;
    }
    if (this.build != o.build) {
      return this.build < o.build ? -1 : 1;
    }
    return 0;
  }

  @Override
  public String toString() {
    return format("%d.%d.%d", major, minor, build);
  }

  public String toVersionString() {
    StringBuilder version = new StringBuilder();
    version.append(major);
    if (minor != 0) {
      version.append('.').append(minor);
    }
    if (build != 0) {
      version.append('.').append(build);
    }
    return version.toString();
  }

  /**
   * Validates the argument {@code version}.
   * 
   * @param version Version string to validate
   */
  public static void validateVersion(String version) {
    new WmsVersion(version);
  }

  private static int versionElement(String[] versionParts, int i) {
    if (versionParts.length < i + 1) {
      return 0;
    }
    String part = versionParts[i];
    if (part.matches("[0-9]+")) {
      return Integer.parseInt(part);
    }
    throw new IllegalArgumentException(
        format("Unsupported version format: %s (non-numeric)", part));
  }

}
