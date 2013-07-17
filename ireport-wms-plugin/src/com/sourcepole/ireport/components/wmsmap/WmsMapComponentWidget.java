package com.sourcepole.ireport.components.wmsmap;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;

public class WmsMapComponentWidget extends JRDesignElementWidget {

  private WmsMapIcon mapIcon = null;

  public WmsMapComponentWidget(AbstractReportObjectScene scene, JRDesignElement element) {
    super(scene, element);

    if (((JRDesignComponentElement) element).getComponent() instanceof StandardWmsMapComponent) {
      StandardWmsMapComponent c = (StandardWmsMapComponent) ((JRDesignComponentElement) element).getComponent();
      // TODO: fix escaping this reference
      c.getEventSupport().addPropertyChangeListener(this);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals(StandardWmsMapComponent.PROPERTY_BBOX_EXPRESSION)
                || evt.getPropertyName().equals(StandardWmsMapComponent.PROPERTY_LAYERS_EXPRESSION)
                || evt.getPropertyName().equals(StandardWmsMapComponent.PROPERTY_STYLES_EXPRESSION)
                || evt.getPropertyName().equals(StandardWmsMapComponent.PROPERTY_URL_PARAMETERS_EXPRESSION)) {
            updateBounds();
            this.repaint();
            this.revalidate(true);
            this.getSelectionWidget().updateBounds();
            this.getSelectionWidget().revalidate(true);
            getScene().validate();
        }

    super.propertyChange(evt);
  }

  @Override
  protected void paintWidgetImplementation() {
    if (mapIcon == null && ((JRDesignComponentElement) getElement()).getComponent() instanceof StandardWmsMapComponent) {
      mapIcon = new WmsMapIcon();
    }

    if (mapIcon != null && mapIcon.getIcon(200) != null) {
      Graphics2D gr = getScene().getGraphics();
      java.awt.Rectangle r = getPreferredBounds();

      AffineTransform af = gr.getTransform();
      AffineTransform new_af = (AffineTransform) af.clone();
      AffineTransform translate = AffineTransform.getTranslateInstance(
              getBorder().getInsets().left + r.x,
              getBorder().getInsets().top + r.y);
      new_af.concatenate(translate);
      gr.setTransform(new_af);

      JRDesignElement e = this.getElement();

      //Composite oldComposite = gr.getComposite();
      Shape oldClip = gr.getClip();
      Shape rect = new Rectangle2D.Float(0, 0, e.getWidth(), e.getHeight());
      gr.clip(rect);

      gr.setPaint(
              new GradientPaint(
              0, e.getHeight(), new Color(255, 255, 255, (int) (0.25 * 255)),
              e.getWidth(), 0, new Color(200, 200, 200, (int) (0.25 * 255))));
      gr.fillRect(0, 0, e.getWidth(), e.getHeight());
      //gr.setComposite(oldComposite);
      if (e.getWidth() > 10) {
        // Calculate the width....
        Image img_to_paint = mapIcon.getIcon(Math.min(mapIcon.getIcon().getIconWidth(), e.getWidth()),
                Math.min(mapIcon.getIcon().getIconHeight(), e.getHeight())).getImage();

        Composite oldComposite = gr.getComposite();
        gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        try {
          gr.drawImage(img_to_paint, e.getWidth() / 2 - img_to_paint.getWidth(null) / 2,
                  e.getHeight() / 2 - img_to_paint.getHeight(null) / 2, null);
        } catch (Exception ex) {
        }
        gr.setComposite(oldComposite);
      }
      gr.setClip(oldClip);

      gr.setTransform(af);

    }

  }
}
