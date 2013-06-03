package com.sourcepole.jasperreports.wmsmap.type;

import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.JREnum;

public enum WmsMapImageTypeEnum implements JREnum
{

  /** The 8-bit PNG format (the same as PNG_8). */
  PNG((byte) 0, "png"),

  /** The 8-bit PNG format. */
  PNG_8((byte) 1, "png8"),

  /** The 32-bit PNG format. */
  PNG_32((byte) 2, "png32"),

  /** The GIF format. */
  GIF((byte) 3, "gif"),

  /** The JPEG compression format. */
  JPG((byte) 4, "jpg");

  private final transient byte value;
  private final transient String name;

  private WmsMapImageTypeEnum(byte value, String name) {
    this.value = value;
    this.name = name;
  }

  @Override
  public Byte getValueByte() {
    return new Byte(value);
  }

  @Override
  public final byte getValue() {
    return value;
  }

  @Override
  public String getName() {
    return name;
  }

  public static WmsMapImageTypeEnum getByName(String name) {
    return (WmsMapImageTypeEnum) EnumUtil.getByName(values(), name);
  }

  public static WmsMapImageTypeEnum getByValue(Byte value) {
    return (WmsMapImageTypeEnum) EnumUtil.getByValue(values(), value);
  }

  public static WmsMapImageTypeEnum getByValue(byte value) {
    return getByValue(new Byte(value));
  }

}
