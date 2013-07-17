package com.sourcepole.ireport.components.wmsmap;

import com.jaspersoft.ireport.locale.I18n;
import java.util.ResourceBundle;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

  @Override
  public void restored() {
    I18n.addBundleLocation(ResourceBundle
        .getBundle("/com/sourcepole/ireport/components/wmsmap/Bundle"));
  }
}
