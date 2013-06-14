package com.sourcepole.ireport.components.wmsmap.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import org.openide.nodes.PropertySupport;
import org.openide.util.WeakListeners;

/**
 * Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
 */
public final class EvaluationGroupProperty extends PropertySupport implements PropertyChangeListener {

  private final JRDesignDataset dataset;
  private final StandardWmsMapComponent component;
  private ComboBoxPropertyEditor editor;

  @SuppressWarnings("unchecked")
  public EvaluationGroupProperty(StandardWmsMapComponent component, JRDesignDataset dataset) {
    // TODO: Replace WhenNoDataType with the right constant
    super(StandardWmsMapComponent.PROPERTY_EVALUATION_GROUP, JRGroup.class,
            "Evaluation group", "Evaluate the expression when the specified group changes", true, true);
    this.component = component;
    this.dataset = dataset;
    setValue("suppressCustomEditor", Boolean.TRUE);

    dataset.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, dataset.getEventSupport()));
  }

  @Override
  public boolean canWrite() {
    return component.getEvaluationTime() == EvaluationTimeEnum.NOW;
  }

  @Override
  @SuppressWarnings("unchecked")
  public PropertyEditor getPropertyEditor() {

    if (editor == null) {

      editor = new ComboBoxPropertyEditor(false, getListOfTags());
    }
    return editor;
  }

  @Override
  public Object getValue() throws IllegalAccessException, InvocationTargetException {
    return component.getEvaluationGroup() == null ? "" : component.getEvaluationGroup();
  }

  @Override
  public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    if (val instanceof String) {
      String oldValue = component.getEvaluationGroup();
      String newValue = (String) val;
      component.setEvaluationGroup(newValue);

      ObjectPropertyUndoableEdit urob =
              new ObjectPropertyUndoableEdit(
              component,
              "EvaluationGroup",
              String.class,
              oldValue, newValue);
      // Find the undoRedo manager...
      IReportManager.getInstance().addUndoableEdit(urob);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (editor == null) {
      return;
    }
    if (evt.getPropertyName() == null) {
      return;
    }
    if (evt.getPropertyName().equals(JRDesignDataset.PROPERTY_GROUPS)
            || evt.getPropertyName().equals(JRDesignGroup.PROPERTY_NAME)) {
      editor.setTagValues(getListOfTags());
    }
  }

  private java.util.ArrayList getListOfTags() {
    java.util.ArrayList l = new java.util.ArrayList();
    List groups = dataset.getGroupsList();
    for (int i = 0; i < groups.size(); ++i) {
      JRDesignGroup group = (JRDesignGroup) groups.get(i);
      l.add(new Tag(group.getName(), group.getName()));
      group.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, group.getEventSupport()));
    }
    return l;
  }
}
